package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName

class GroupData(
    @SerializedName("ID") val id : Int = -1,
    @SerializedName("Group") val group : String ="",
    @SerializedName("Old") val old : Int= -1,
    @SerializedName("HeadTUID") val headTuid : Int=-1) : BaseData() {
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
        return group
    }
}