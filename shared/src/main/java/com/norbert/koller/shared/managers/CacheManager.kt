package com.norbert.koller.shared.managers

import android.content.Context
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData

object CacheManager {

    var listDataMap : MutableMap<String, ExpiringListData> = mutableMapOf()


    suspend fun getListDataMapWithValues(context: Context, dataTag : String, classOfT : Class<*>) : List<BaseData>{

        val list = mutableListOf<BaseData>()
        for (listData in listDataMap[dataTag]!!.list){
            val baseData : BaseData
            if(detailsDataMap.containsKey(Pair(dataTag, listData))){
                baseData = detailsDataMap[Pair(dataTag, listData)]!!
            }
            else{
                baseData = DataStoreManager.readDetail(context, dataTag, listData, classOfT)!!
                detailsDataMap[Pair(dataTag, listData)] = baseData
            }
            list.add(baseData)
        }

        return list
    }

    var detailsDataMap : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

    var loginData : LoginTokensData? = null

    var userData: UserData = UserData()
}