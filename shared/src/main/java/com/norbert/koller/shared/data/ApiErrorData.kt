package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

data class ApiErrorData(
    var error : String,
    @SerializedName("error_description") var errorDescription : String
)
