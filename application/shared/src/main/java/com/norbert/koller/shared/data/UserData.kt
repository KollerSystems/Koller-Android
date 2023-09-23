package com.norbert.koller.shared.data

import android.content.Context

class ClassData(val ID : Int = 0, val Class : String ="", val HeadTUID : Int=1)

class UserData (
    var UID : Int = -1,
    val OM : Long? = null,
    val Gender : Int? = null,
    val Name : String? = null,
    val Picture : String? = null,
    val Group : String? = null,
    val School : String? = null,
    val Birthplace : String? = null,
    val Birthdate : String? = null,
    val GuardiaName : String? = null,
    val GuardianPhone : String? = null,
    val RID : Int? = null,
    val Country : String ? = null,
    val City : String? = null,
    val Street : String? = null,
    val PostCode : String? = null,
    val Address : String? = null,
    val Floor : String? = null,
    val Door : String? = null,
    val Email : String? = null,
    val Discord : String? = null,
    val Facebook : String? = null,
    val Instagram : String? = null,
    val BedNum : Int? = null,
    val Class : ClassData? = null
) : BaseData()
{


    companion object {
        var instance: UserData = UserData()
    }

    override fun diffrentDecider(context: Context): String {
        return Name!![0].toString()
    }

    override fun getMainID(): Int {
        return UID
    }


}