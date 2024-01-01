package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

abstract class ApiLoginData (
    @SerializedName("grant_type") val grantType : String
)

class ApiLoginRefreshData(
    grantType : String,
    @SerializedName("refresh_token") val refreshToken : String
) : ApiLoginData(grantType)

class ApiLoginUsernameAndPasswordData(
    grant_type : String,
    val username : String,
    val password : String
) : ApiLoginData(grant_type)
