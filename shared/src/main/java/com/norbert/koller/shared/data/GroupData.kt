package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

class GroupData(
    @SerializedName("ID") val id : Int = -1,
    @SerializedName("Group") val group : String ="",
    @SerializedName("Old") val old : Int= -1,
    @SerializedName("HeadTUID") val headTuid : Int=-1)