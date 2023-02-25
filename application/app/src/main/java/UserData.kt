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
    val Address : String,
    val RoomNumber : String
){
    companion object {
        lateinit var instance: UserData
    }

    init {
        instance = this
    }
}