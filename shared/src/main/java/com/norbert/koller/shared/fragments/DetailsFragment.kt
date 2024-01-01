package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.customviews.FullScreenLoading
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.recycleradapters.UserPreviewRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.skydoves.androidveil.VeilLayout
import okhttp3.Response

abstract class DetailsFragment(val id : Int? = null) : Fragment() {

    lateinit var loadingOl : FullScreenLoading
    lateinit var viewModel: ResponseViewModel

    abstract fun getDataTag() : String

    abstract fun apiFunctionToCall() : suspend () -> retrofit2.Response<*>

    abstract fun getVeils() : List<VeilLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        loadingOl = view.findViewById(R.id.loading_overlay)

        if(savedInstanceState == null){
            viewModel.id = id!!

            val key = Pair(getDataTag(), viewModel.id)
            if(CacheManager.savedValues.containsKey(key)){
                viewModel.response.value = CacheManager.savedValues[key]
            }else{
                if(CacheManager.savedListsOfValues.containsKey(getDataTag())) {
                    var foundIndex : Int? = null

                    CacheManager.savedListsOfValues[getDataTag()]!!.forEachIndexed{ i, value->
                        if(value.getMainID() == viewModel.id){
                            foundIndex = i
                        }
                    }
                    if(foundIndex != null){
                        viewModel.response.value = CacheManager.savedListsOfValues[getDataTag()]!![foundIndex!!]
                        loadData()
                        enableVeils()

                    }
                    else{
                        loadingOl.loadData = {loadData()}
                    }
                }
                else{
                    loadingOl.loadData = {loadData()}
                }
            }

        }
    }

    fun loadData(){

        RetrofitInstance.communicate(lifecycleScope, apiFunctionToCall(),
            {
                viewModel.response.value = it as BaseData
                CacheManager.savedValues[Pair(getDataTag(), it.getMainID())] = it
                loadingOl.setState(FullScreenLoading.NONE)
                disableVeils()
            },
            {errorMsg, errorBody ->
                loadingOl.setState(FullScreenLoading.ERROR)
            })
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

}