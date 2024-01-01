package com.norbert.koller.shared.managers

import com.norbert.koller.shared.data.BaseData

object CacheManager {

    var savedListsOfValues : MutableMap<String, ArrayList<BaseData>> = mutableMapOf()

    var savedValues : MutableMap<Pair<String, Int>, BaseData> = mutableMapOf()

}