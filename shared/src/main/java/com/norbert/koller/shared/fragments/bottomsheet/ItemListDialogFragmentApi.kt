package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ClassData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.recycleradapters.ListItem
import kotlinx.coroutines.launch
import retrofit2.Response

class ItemListDialogFragmentApi(val apiToCall : suspend () -> Response<*>, alreadyChecked : ArrayList<String>? = null, val key : String) : ItemListDialogFragmentBase(alreadyChecked) {



    fun responseToListItemList(response : ArrayList<BaseData>) : ArrayList<ListItem>{
        var listItemList: ArrayList<ListItem> = arrayListOf()
        for (i in response.indices){
            var listItem = ListItem(response[i].getTitle(), null, null, response[i].getMainID().toString())
            listItemList.add(listItem)
        }

        return listItemList
    }

    fun setRecyclerViewWitResponse(response : ArrayList<BaseData>) : ArrayList<ListItem>{
        val listItemList = responseToListItemList(response)
        setRecyclerView(listItemList)

        return listItemList
    }

    override fun toggleList(): Boolean {
        return getValuesOnFinish != null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            if(CacheManager.savedListsOfValues.containsKey(key)){
                list = setRecyclerViewWitResponse(CacheManager.savedListsOfValues[key]!!)
            }
            else{
                RetrofitInstance.communicate(apiToCall, {
                    val arrayList : ArrayList<BaseData> = ArrayList(it as List<ClassData>)
                    list = setRecyclerViewWitResponse(arrayList)
                    CacheManager.savedListsOfValues[key] = arrayList
                },{ error, errorBody ->

                })
            }
        }



    }

}