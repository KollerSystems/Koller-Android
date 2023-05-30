package com.example.koller.data

data class UserData (
    val ID : Int = -1,
    val OM : String? = null,
    val Name : String? = null,
    val Picture : String? = null,
    val Group : String? = null,
    val Class : String? = null,
    val School : String? = null,
    val Birthplace : String? = null,
    val Birthdate : String? = null,
    val GuardiaName : String? = null,
    val GuardianPhone : String? = null,
    val RoomNumber : String? = null,
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
    val Instagram : String? = null
){
    companion object {
        var instance: UserData = UserData()
    }


}