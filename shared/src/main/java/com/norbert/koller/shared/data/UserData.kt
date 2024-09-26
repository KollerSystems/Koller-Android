package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName

class UserData(
    @SerializedName("UID") var uid : Int = -1,
    @SerializedName("OM") var om : Long? = null,
    @SerializedName("Role") var role : Int? = null,
    @SerializedName("Name") var name : String? = null,
    @SerializedName("Gender") var gender : Int? = null,
    @SerializedName("Picture") var picture : String? = null,
    @SerializedName("School") val school : String? = null,
    @SerializedName("Birthplace") val birthplace : String? = null,
    @SerializedName("Birthdate") val birthdate : String? = null,
    @SerializedName("GuardiaName") val guardiaName : String? = null,
    @SerializedName("GuardianPhone") val guardianPhone : String? = null,
    @SerializedName("RID") var rid : Int? = null,
    @SerializedName("PID") var pid : Int? = null,
    @SerializedName("BedNum") val bedNum : Int? = null,
    @SerializedName("Country") val country : String ? = null,
    @SerializedName("City") val city : String? = null,
    @SerializedName("Street") val street : String? = null,
    @SerializedName("PostCode") val postCode : String? = null,
    @SerializedName("Address") val address : String? = null,
    @SerializedName("Floor") val floor : String? = null,
    @SerializedName("Door") val door : String? = null,

    @SerializedName("Class") var class_ : ClassData? = null,
    @SerializedName("Group") var group : GroupData? = null,
    @SerializedName("Contacts") val contacts : ContactsData? = null

) : BaseData()
{
    override fun diffrentDecider(context: Context): String {
        if(name == null){
            return "NO NAME"
        }
        return name!![0].toString()
    }

    override fun getMainID(): Int {
        return uid
    }

    fun createDescription(): String{
        return if(role == 1){
            group?.group + " • " + rid  + " • " + class_?.class_
        }
        else{
            var string = ""
            if(group != null){
                string = group?.group + " • "
            }
            string += (rid.toString() + " • " + pid)
            string
        }
    }

    override fun updateValues(baseData: BaseData) {
        baseData as UserData
        picture = baseData.picture
        name = baseData.name
        gender = baseData.gender
        rid = baseData.rid
        class_ = baseData.class_
        group = baseData.group
        pid = baseData.pid
    }
}