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
            var baseData = getDetailsDataMapValue(classOfT.simpleName, listData)
            if(baseData == null){
                baseData = DataStoreManager.readDetail(context, listData, classOfT)!!
                setDetailsDataMapValue(classOfT.simpleName, listData, baseData)
            }
            list.add(baseData)
        }

        return list
    }

    var detailsDataMap : MutableMap<String, (MutableMap<Int, BaseData>)> = mutableMapOf()

    fun getDetailsDataMapValue(category : String, id : Int) : BaseData?{
        return getDetailsDataMapValue(Pair(category, id))
    }

    fun getDetailsDataMapValue(pair : Pair<String, Int>) : BaseData?{
        val baseData = detailsDataMap[pair.first]?.get(pair.second)

        Log.d("CM:GetDetails", Gson().toJson(baseData))
        return baseData
    }

    var loginData : LoginTokensData? = null

    var currentUserId: Int? = null

    fun setDetailsDataMapValue(category : String, id : Int, baseData: BaseData){
        if(!detailsDataMap.containsKey(category)){
            detailsDataMap[category] = mutableMapOf()
        }
        detailsDataMap[category]!![id] = baseData
    }

    fun getCurrentUserData() : UserData?{
        return detailsDataMap[UserData::class.simpleName]?.get(currentUserId) as UserData?
    }

    fun updateCurrentUserData(userData: UserData){
        detailsDataMap[UserData::class.simpleName!!]!![userData.uid] = userData
        currentUserId = userData.uid
    }
}