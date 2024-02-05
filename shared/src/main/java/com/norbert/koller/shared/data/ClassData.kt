package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName

class ClassData(
    @SerializedName("ID") val id : Int = -1,
    @SerializedName("Class") val class_ : String ="",
    @SerializedName("Old") val old : Int=-1) : BaseData() {
    override fun diffrentDecider(context: Context): String {
        return if(old == 0){
            "Élő"
        }
        else{
            "Régi"
        }
    }

    override fun getMainID(): Int {
        return id
    }

    override fun getTitle(): String {
        return class_
    }
}