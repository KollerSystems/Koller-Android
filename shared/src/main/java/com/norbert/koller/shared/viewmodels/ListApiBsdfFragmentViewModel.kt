package com.norbert.koller.shared.viewmodels

import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListItem
import retrofit2.Response

class ListApiBsdfFragmentViewModel : ListBsdfFragmentViewModel() {

    lateinit var key : String

    suspend fun call(apiToCall : (suspend () -> Response<*>)){
        RetrofitInstance.communicate(apiToCall, {
            val arrayList : ArrayList<BaseData> = ArrayList(it as List<BaseData>)
            list.value = responseToListItemList(arrayList)
            CacheManager.savedListsOfValues[key] = arrayList



        },{ error, errorBody ->

            list.value = null


        })
    }

    fun responseToListItemList(response : ArrayList<BaseData>) : ArrayList<ListItem>{
        var listItemList: ArrayList<ListItem> = arrayListOf()
        for (i in response.indices){
            var listItem = ListItem(response[i].getTitle(), response[i].getDescription(), null, response[i].getMainID().toString())
            listItemList.add(listItem)
        }

        return listItemList
    }
}