package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

data class LoginTokensResponseData (
        @SerializedName("access_token") val accessToken : String,
        @SerializedName("token_type") val tokenType : String,
        @SerializedName("expires_in") val expiresIn : Long,
        @SerializedName("refresh_token") val refreshToken : String
        )

