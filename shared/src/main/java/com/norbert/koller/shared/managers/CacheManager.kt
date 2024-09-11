package com.norbert.koller.shared.managers

import com.google.gson.annotations.Until
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.UserData
import okhttp3.Response

object CacheManager {

    var listDataMap : MutableMap<String, MutableList<Int>> = mutableMapOf()

    fun getListDataMapWithValues(dataTag : String) : List<BaseData>{
        val list = mutableListOf<BaseData>()
        for (listData in listDataMap[dataTag]!!){
            list.add(detailsDataMap[Pair(dataTag, listData)]!!)
        }
        return list
    }

    var detailsDataMap : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

    var loginData : LoginTokensData? = null

    var userData: UserData = UserData()
}