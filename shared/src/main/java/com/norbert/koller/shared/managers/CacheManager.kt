package com.norbert.koller.shared.managers

import android.content.Context
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.ExpiringListData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.data.UserData

object CacheManager {

    var listDataMap : MutableMap<String, ExpiringListData> = mutableMapOf()


    suspend fun getListDataMapWithValues(context: Context, classOfT : Class<*>) : List<BaseData>{

        val list = mutableListOf<BaseData>()
        for (listData in listDataMap[classOfT.simpleName]!!.list){
            val baseData : BaseData
            if(detailsDataMap.containsKey(Pair(classOfT.simpleName, listData))){
                baseData = detailsDataMap[Pair(classOfT.simpleName, listData)]!!
            }
            else{
                baseData = DataStoreManager.readDetail(context, listData, classOfT)!!
                detailsDataMap[Pair(classOfT.simpleName, listData)] = baseData
            }
            list.add(baseData)
        }

        return list
    }

    var detailsDataMap : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

    var loginData : LoginTokensData? = null

    var userData: UserData = UserData()
}