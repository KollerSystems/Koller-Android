package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

data class ErrorData(
    var error : String,
    @SerializedName("error_description") var errorDescription : String
)
