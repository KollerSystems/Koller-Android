package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.orderSingleNumber

class RoomData(
    @SerializedName("RID") var rid : Int = -1,
    @SerializedName("Group") var group : GroupData? = GroupData(),
    @SerializedName("Annexe") var annexe : AnnexeData? = AnnexeData(),
    @SerializedName("Residents") var residents : ArrayList<UserData>?,
) : BaseData() {

    override fun diffrentDecider(context: Context): String {

        val genderString: String

        if(annexe == null){
            return rid.toString()[0].orderSingleNumber(context) + " " + context.getString(R.string.floor_level).lowercase()
        }

        return rid.toString()[0].orderSingleNumber(context) + " " + context.getString(R.string.floor_level).lowercase() + " • " + annexe?.annexe
    }

    override fun getMainID(): Int {
        return rid
    }
}