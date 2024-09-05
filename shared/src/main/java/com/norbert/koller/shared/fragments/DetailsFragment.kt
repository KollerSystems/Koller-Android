package com.norbert.koller.shared.fragments

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.LoadingOverlayView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


abstract class DetailsFragment(val id : Int? = null, val baseData : BaseData? = null) : FragmentInMainActivity() {

    lateinit var viewModel: DetailsViewModel

    abstract fun getDataTag() : String

    abstract fun apiFunctionToCall() : suspend () -> retrofit2.Response<*>


    abstract fun createRootView() : View

    var snackbar : Snackbar? = null

    lateinit var swrl : SwipeRefreshLayout

    open fun setupTransition(view : View){
        ViewCompat.setTransitionName(view, "cardTransition_${id?:baseData!!.getMainID()}position")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = createRootView()
        setupTransition(view.rootView)
        return view
    }

    abstract fun getTimeLimit() : Int

    fun createLoadingOverlay() : LoadingOverlayView{
        val loadingOl = LoadingOverlayView(requireContext())
        loadingOl.setLayoutParams(LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        (swrl.parent as ViewGroup).addView(loadingOl)

        viewModel.onLoadSuccess = {
            loadingOl.setState(DetailsViewModel.NONE)
        }

        viewModel.onLoadError = {
            loadingOl.setState(DetailsViewModel.ERROR)
        }

        loadingOl.loadData = {
            viewModel.load(apiFunctionToCall(), getDataTag())
        }
        return loadingOl
    }

    abstract fun getDataType() : Class<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        swrl = view.findViewById(R.id.swrl)
        swrl.setOnRefreshListener {
            refresh()
        }

        viewModel.onRefreshSuccess = {
            snackbar?.dismiss()
            swrl.isRefreshing = false
        }

        viewModel.onRefreshError = {
            createSnackBar()
            swrl.isRefreshing = false
        }

        if(!viewModel.response.isInitialized) {

            if (viewModel.id == null) {
                viewModel.id = id?:baseData!!.getMainID()

                val key = Pair(getDataTag(), viewModel.id)
                if (CacheManager.savedValues.containsKey(key)) {

                    if (!CacheManager.savedValues[key]!!.isUnexpired(getTimeLimit())) {
                        refresh()
                    }

                    viewModel.response.value = CacheManager.savedValues[key]!!
                    return
                }

                if(baseData != null){
                    viewModel.response.value = baseData
                    refresh()
                    return
                }

                if (CacheManager.savedListsOfValues.containsKey(getDataTag())) {
                    var foundIndex: Int? = null

                    CacheManager.savedListsOfValues[getDataTag()]!!.forEachIndexed { i, value ->
                        if (value.getMainID() == viewModel.id) {
                            foundIndex = i
                        }
                    }

                    if (foundIndex != null) {

                        viewModel.updateValues(CacheManager.savedListsOfValues[getDataTag()]!![foundIndex!!], getDataTag())

                        refresh()
                        return
                    }
                }

                val loadingOverlay = createLoadingOverlay()

                lifecycleScope.launch {
                    val baseData = DataStoreManager.readDetail(requireContext(), getDataTag(), viewModel.id!!, getDataType())
                    if(baseData != null){
                        viewModel.response.value = baseData
                        loadingOverlay.setState(DetailsViewModel.NONE)
                        if(!baseData.isUnexpired(getTimeLimit())){
                            refresh()
                        }
                        return@launch
                    }

                    loadingOverlay.loadData!!.invoke()

                }

            } else {

                createLoadingOverlay().setState(viewModel.state)
            }
        }
        else{
            when(viewModel.state){
                DetailsViewModel.LOADING ->{
                    swrl.isRefreshing = true
                }
                DetailsViewModel.ERROR->{
                    createSnackBar()
                }
            }
        }
    }

    fun refresh(){

        viewModel.refresh(apiFunctionToCall(), getDataTag())

        swrl.isRefreshing = true
    }



    fun createSnackBar(){
        snackbar = ApiHelper.createSnackBar(requireContext()){
            refresh()
        }
    }

    override fun onStop() {
        super.onStop()

        snackbar?.dismiss()

    }

}