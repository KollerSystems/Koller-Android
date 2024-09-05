package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName

class UserData(
    @SerializedName("UID") var uid : Int = -1,
    @SerializedName("OM") val om : Long? = null,
    @SerializedName("Name") val name : String? = null,
    @SerializedName("Gender") val gender : Int? = null,
    @SerializedName("Picture") val picture : String? = null,
    @SerializedName("School") val school : String? = null,
    @SerializedName("Birthplace") val birthplace : String? = null,
    @SerializedName("Birthdate") val birthdate : String? = null,
    @SerializedName("GuardiaName") val guardiaName : String? = null,
    @SerializedName("GuardianPhone") val guardianPhone : String? = null,
    @SerializedName("RID") val rid : Int? = null,
    @SerializedName("BedNum") val bedNum : Int? = null,
    @SerializedName("Country") val country : String ? = null,
    @SerializedName("City") val city : String? = null,
    @SerializedName("Street") val street : String? = null,
    @SerializedName("PostCode") val postCode : String? = null,
    @SerializedName("Address") val address : String? = null,
    @SerializedName("Floor") val floor : String? = null,
    @SerializedName("Door") val door : String? = null,

    @SerializedName("Class") val class_ : ClassData? = null,
    @SerializedName("Group") val group : GroupData? = null,
    @SerializedName("Contacts") val contacts : ContactsData? = null

) : BaseData()
{

    companion object {
        var instance: UserData = UserData()
    }

    override fun diffrentDecider(context: Context): String {
        if(name == null){
            return "NO NAME"
        }
        return name[0].toString()
    }

    override fun getMainID(): Int {
        return uid
    }

    fun createDescription(): String{
        return group?.group + " • " + rid  + " • " + class_?.class_
    }


}