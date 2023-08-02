package com.example.shared.data

import android.app.Activity
import android.content.Context
import com.example.shared.MyApplication
import com.example.shared.R

class RoomData(
    var RID : Int = -1,
    var Gender : Int?,
    var Group : String?,
    var residents : ArrayList<UserData>?,
) : BaseData() {
    override fun diffrentDecider(context: Context): String {

        var genderString: String

        if(Gender == 0){
            genderString = context.getString(R.string.girl_side)
        }
        else{
            genderString = context.getString(R.string.boy_side)
        }



        return MyApplication.orderSingleNumber(context, RID.toString()[0].toString()) + " " + context.getString(R.string.floor_level).lowercase() + " • " + genderString
    }

    override fun getID(): Int {
        return RID
    }
}