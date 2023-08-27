package com.norbert.koller.shared.data

data class ApiLoginRefreshData(
    val grant_type : String,
    val refresh_token : String
    )
