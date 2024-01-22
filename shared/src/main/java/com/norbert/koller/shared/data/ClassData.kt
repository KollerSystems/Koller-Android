package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

class ClassData(
    @SerializedName("ID") val id : Int = -1,
    @SerializedName("Class") val class_ : String ="",
    @SerializedName("Old") val old : Int=-1)