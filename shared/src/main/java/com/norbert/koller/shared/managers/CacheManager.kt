package com.norbert.koller.shared.managers

import android.content.Context
import android.util.Log
import com.google.gson.Gson
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

    fun getDetailsDataMap(category : String, id : Int) : BaseData?{
        return getDetailsDataMap(Pair(category, id))
    }

    fun getDetailsDataMap(pair : Pair<String, Int>) : BaseData?{
        val baseData = detailsDataMap[pair]

        Log.d("CM:GetDetails", Gson().toJson(baseData))
        return baseData
    }

    var loginData : LoginTokensData? = null

    var currentUserId: Int? = null

    fun getCurrentUserData() : UserData?{
        return detailsDataMap[Pair(UserData::class.simpleName, currentUserId)] as UserData?
    }

    fun updateCurrentUserData(userData: UserData){
        detailsDataMap[Pair(UserData::class.simpleName!!, userData.uid)] = userData
        currentUserId = userData.uid
    }
}