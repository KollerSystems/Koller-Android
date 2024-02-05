package com.norbert.koller.shared.managers

import com.google.gson.annotations.Until
import com.norbert.koller.shared.api.RetrofitInstance
import com.norbert.koller.shared.data.BaseData
import okhttp3.Response

object CacheManager {

    var savedListsOfValues : MutableMap<String, ArrayList<BaseData>> = mutableMapOf()

    var savedValues : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

}