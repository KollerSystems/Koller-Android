package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

data class ApiLoginTokensData (
        @SerializedName("access_token") val accessToken : String = "",
        @SerializedName("token_type") val tokenType : String = "",
        @SerializedName("expires_in") val expiresIn : Int = -1,
        @SerializedName("refresh_token") val refreshToken : String = ""
        )

