package com.norbert.koller.shared.viewmodels

import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListToggleItem
import retrofit2.Response

class ListToggleApiBsdfFragmentViewModel : ListToggleBsdfFragmentViewModel() {

    lateinit var classOfT: Class<*>

    suspend fun call(apiToCall : (suspend () -> Response<*>)){
        RetrofitInstance.communicate(apiToCall, {
            val responseList : List<BaseData> = it as List<BaseData>
            list.value = responseToListItemList(responseList)

            if (!CacheManager.listDataMap.containsKey(classOfT.simpleName)) {
                CacheManager.listDataMap[classOfT.simpleName] = ExpiringListData()
                CacheManager.listDataMap[classOfT.simpleName]!!.saveReceivedTime()
            }
            for (item in responseList){
                CacheManager.detailsDataMap[Pair(classOfT.simpleName, item.getMainID())] = item
                CacheManager.listDataMap[classOfT.simpleName]!!.list.add(item.getMainID())
            }
        },{ error, errorBody ->

            list.value = null
        })
    }

    fun responseToListItemList(response : List<BaseData>) : ArrayList<ListItem>{
        var listItemList: ArrayList<ListItem> = arrayListOf()
        for (i in response.indices){
            var listItem = ListToggleItem(response[i].getTitle(), response[i].getDescription(), null, response[i].getMainID().toString())
            listItemList.add(listItem)
        }

        return listItemList
    }
}