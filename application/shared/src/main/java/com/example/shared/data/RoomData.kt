package com.example.shared.data

class RoomData(
    var RID : Int = -1,
    var Gender : Int?,
    var Group : String?,
    var residents : ArrayList<UserData>?
) : BaseData() {
    override fun diffrentDecider(): String {

        return RID.toString()[0] + ". emelet"
    }

    override fun getID(): Int {
        return RID
    }
}