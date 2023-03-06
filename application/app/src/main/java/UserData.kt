data class UserData (
    val ID : Int,
    val OM : String,
    val Name : String,
    val Picture : String,
    val Group : String,
    val Class : String,
    val School : String,
    val Birthplace : String,
    val Birthdate : String,
    val GuardiaName : String,
    val GuardianPhone : String,
    val RoomNumber : String,
    val Country : String,
    val City : String,
    val Street : String,
    val PostCode : String,
    val Address : String,
    val Floor : String,
    val Door : String,
){
    companion object {
        lateinit var instance: UserData
    }

    init {
        instance = this
    }
}