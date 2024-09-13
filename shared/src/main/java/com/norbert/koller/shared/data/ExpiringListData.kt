package com.norbert.koller.shared.data

class ExpiringListData (
    var list : MutableList<Int> = mutableListOf()
) : ExpiringData()