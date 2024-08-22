package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.skydoves.androidveil.VeilLayout


abstract class DetailsFragment(val id : Int? = null) : FragmentInMainActivity() {



    lateinit var loadingOl : FullScreenLoading
    lateinit var viewModel: ResponseViewModel

    abstract fun getDataTag() : String

    abstract fun apiFunctionToCall() : suspend () -> retrofit2.Response<*>


    abstract fun createRootView() : View

    var snackbar : Snackbar? = null

    lateinit var swrl : SwipeRefreshLayout

    open fun setupTransition(view : View){
        ViewCompat.setTransitionName(view, "cardTransition_${id}position")
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        viewModel.onLoadSuccess = {
            loadingOl.setState(ResponseViewModel.NONE)
        }

        viewModel.onLoadError = {
            loadingOl.setState(ResponseViewModel.ERROR)
        }

        viewModel.onRefreshSuccess = {
            if(snackbar != null){
                snackbar!!.dismiss()
            }
            swrl.isRefreshing = false
        }

        viewModel.onRefreshError = {
            createSnackBar()
            swrl.isRefreshing = false
        }

        swrl = view.findViewById(R.id.swrl)
        loadingOl = FullScreenLoading(requireContext())
        loadingOl.setLayoutParams(LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        (swrl.parent as ViewGroup).addView(loadingOl)
        swrl.setOnRefreshListener {
            refresh()
        }

        if(!viewModel.response.isInitialized) {

            loadingOl.loadData = {
                viewModel.load(apiFunctionToCall(), getDataTag())
            }

            if (viewModel.id == null) {
                viewModel.id = id!!

                val key = Pair(getDataTag(), viewModel.id)
                if (CacheManager.savedValues.containsKey(key)) {
                    if (!ApplicationManager.isOnline(requireContext())) {
                        createSnackBar()
                    } else {
                        if (!CacheManager.savedValues[key]!!.isUnexpired(getTimeLimit())) {
                            refresh()
                        }
                    }
                    viewModel.response.value = CacheManager.savedValues[key]!!
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
                        viewModel.response.value =
                            CacheManager.savedListsOfValues[getDataTag()]!![foundIndex!!]
                        refresh()
                        return
                    }
                }

                loadingOl.loadData!!.invoke()
                return

            } else {

                loadingOl.setState(viewModel.state)
            }
        }
        else
        {
            loadingOl.rootView.isVisible = false
        }
    }

    fun refresh(){

        viewModel.refresh(apiFunctionToCall())
    }



    fun createSnackBar(){
        snackbar = (context as MainActivity).getSnackBar("Friss adatok lekérése sikertelen", Snackbar.LENGTH_INDEFINITE)
        snackbar!!.setAction(getString(R.string.retry)){
            refresh()
        }
        snackbar!!.view.viewTreeObserver
            .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    snackbar!!.view.viewTreeObserver.removeOnPreDrawListener(this)
                    (snackbar!!.view.layoutParams as CoordinatorLayout.LayoutParams).behavior =
                        null
                    return true
                }
            })
        snackbar!!.show()
    }

    override fun onStop() {
        super.onStop()
        if(snackbar != null){
            snackbar!!.dismiss()
        }
    }

}