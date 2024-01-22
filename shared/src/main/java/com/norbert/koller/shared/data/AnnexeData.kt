package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

class AnnexeData(
    @SerializedName("ID") var id : Int = -1,
    @SerializedName("Annexe") var annexe : String = "",
    @SerializedName("Gender") var gender : Int? = null,
)