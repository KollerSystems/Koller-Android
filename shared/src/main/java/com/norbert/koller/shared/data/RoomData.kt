package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.R
import com.norbert.koller.shared.managers.orderSingleNumber

class RoomData(
    @SerializedName("RID") var rid : Int = -1,
    @SerializedName("Group") var group : GroupData?,
    @SerializedName("Annexe") var annexe : AnnexeData?,
    @SerializedName("Residents") var residents : MutableList<UserData>?,
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

    override fun updateValues(baseData: BaseData) {
        baseData as RoomData
        group = baseData.group
        annexe = baseData.annexe
        if(baseData.residents == null) residents = null
        if(residents == null) return
        val residentList = baseData.residents

        for (i in 0 until residentList!!.size){
            for(oldResident in residents!!){
                if(oldResident.uid == residentList[i].uid){
                    residentList[i] = oldResident
                    residentList[i].name = baseData.residents!![i].name
                }
            }
        }
        residents = residentList
    }

    override fun isFull() : Boolean{
        if(residents != null){
            if(residents!![0].picture == null){
                return false
            }
        }
        return true
    }
}