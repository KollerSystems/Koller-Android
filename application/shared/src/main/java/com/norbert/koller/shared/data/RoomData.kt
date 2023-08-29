package com.norbert.koller.shared.data

import android.content.Context
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R

class RoomData(
    var RID : Int = -1,
    var Gender : Int?,
    var Group : String?,
    var Residents : ArrayList<UserData>?,
) : BaseData() {
    override fun diffrentDecider(context: Context): String {

        val genderString: String

        genderString = if(Gender == 0){
            context.getString(R.string.girl_side)
        } else{
            context.getString(R.string.boy_side)
        }



        return MyApplication.orderSingleNumber(context, RID.toString()[0].toString()) + " " + context.getString(R.string.floor_level).lowercase() + " • " + genderString
    }

    override fun getID(): Int {
        return RID
    }
}