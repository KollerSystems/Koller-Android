package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.orderSingleNumber

class RoomData(
    @SerializedName("RID") var rid : Int = -1,
    @SerializedName("Gender") var gender : Int?,
    @SerializedName("Group") var group : String?,
    @SerializedName("Residents") var residents : ArrayList<UserData>?,
) : BaseData() {
    override fun diffrentDecider(context: Context): String {

        val genderString: String

        genderString = if(gender == 0){
            context.getString(R.string.girl_side)
        } else{
            context.getString(R.string.boy_side)
        }



        return rid.toString()[0].orderSingleNumber(context) + " " + context.getString(R.string.floor_level).lowercase() + " • " + genderString
    }

    override fun getMainID(): Int {
        return rid
    }
}