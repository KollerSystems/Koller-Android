package com.norbert.koller.shared.managers

import com.google.gson.annotations.Until
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.LoginTokensData
import com.norbert.koller.shared.data.UserData
import okhttp3.Response

object CacheManager {

    var savedListsOfValues : MutableMap<String, ArrayList<BaseData>> = mutableMapOf()

    var savedValues : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

    var loginData : LoginTokensData? = null

    var userData: UserData = UserData()
}