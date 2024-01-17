package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
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
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.skydoves.androidveil.VeilLayout

abstract class DetailsFragment(val id : Int? = null) : Fragment() {



    lateinit var loadingOl : FullScreenLoading
    lateinit var viewModel: ResponseViewModel

    abstract fun getDataTag() : String

    abstract fun apiFunctionToCall() : suspend () -> retrofit2.Response<*>

    abstract fun getVeils() : List<VeilLayout>

    abstract fun getLayout() : Int

    var snackbar : Snackbar? = null

    lateinit var swrl : SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(getLayout(), container, false)
        ViewCompat.setTransitionName(view!!.findViewById(R.id.fl_root), "cardTransition_${id}position")
        return view
    }

    abstract fun getTimeLimit() : Int


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        swrl = view.findViewById(R.id.swrl)
        loadingOl = view.findViewById(R.id.loading_overlay)

        swrl.setOnRefreshListener {
            refresh()
        }

        if(viewModel.id == null){
            viewModel.id = id!!

            val key = Pair(getDataTag(), viewModel.id)
            if(CacheManager.savedValues.containsKey(key)){
                if(!ApplicationManager.isOnline(requireContext())){
                    CacheManager.savedValues[key]!!.testState = ""
                    createSnackBar()
                }
                else{
                    if(!CacheManager.savedValues[key]!!.isUnexpired(getTimeLimit())){
                        refresh()
                    }
                }
                viewModel.response.value = CacheManager.savedValues[key]!!
                return
            }

            if(CacheManager.savedListsOfValues.containsKey(getDataTag())) {
                var foundIndex : Int? = null

                CacheManager.savedListsOfValues[getDataTag()]!!.forEachIndexed{ i, value->
                    if(value.getMainID() == viewModel.id){
                        foundIndex = i
                    }
                }

                if(foundIndex != null){
                    viewModel.response.value = CacheManager.savedListsOfValues[getDataTag()]!![foundIndex!!]
                    refresh()
                    enableVeils()
                    return
                }
            }

            loadFromZero()
            return
        }

        if((viewModel.response.value as BaseData).testState != "hello"){
            refresh()
            enableVeils()
        }
    }

    fun refresh(){

        RetrofitInstance.communicate(lifecycleScope, apiFunctionToCall(),
            {
                val baseData = it as BaseData
                baseData.saveReceivedTime()
                viewModel.response.value = baseData
                (viewModel.response.value as BaseData).testState = "hello"
                CacheManager.savedValues[Pair(getDataTag(), baseData.getMainID())] = baseData
                disableVeils()
                if(snackbar != null){
                    snackbar!!.dismiss()
                }
                swrl.isRefreshing = false
            },
            {errorMsg, errorBody ->
                createSnackBar()
                swrl.isRefreshing = false
            }
        )
    }

    fun createSnackBar(){
        snackbar = (context as MainActivity).getSnackBar("Friss adatok lekérése sikertelen", Snackbar.LENGTH_INDEFINITE)
        snackbar!!.setAction(getString(R.string.retry)){
            refresh()
        }
        snackbar!!.show()
    }

    private fun loadFromZero(){

        Log.d("LOAFOAFOFORM ZEROOOOOOOOOOO", "FROM ZERRO")

        loadingOl.loadData = {
            RetrofitInstance.communicate(lifecycleScope, apiFunctionToCall(),
                {
                    val baseData = it as BaseData
                    baseData.saveReceivedTime()
                    viewModel.response.value = baseData
                    CacheManager.savedValues[Pair(getDataTag(), baseData.getMainID())] = baseData
                    loadingOl.setState(FullScreenLoading.NONE)
                    disableVeils()
                },
                {errorMsg, errorBody ->
                    loadingOl.setState(FullScreenLoading.ERROR)
                }
            )
        }
    }

    fun enableVeils(){
        for (veil in getVeils()){
            veil.veil()
        }
    }

    fun disableVeils(){
        for (veil in getVeils()){
            veil.unVeil()
        }
    }

    override fun onStop() {
        super.onStop()
        if(snackbar != null){
            snackbar!!.dismiss()
        }
    }

}