data class UserData (
    val ID : Int = 0,
    val OM : String = "12345678910",
    val Name : String = "Teszt Géza",
    val Picture : String = "0",
    val Group : String = "0N",
    val Class : String = "0.Z",
    val School : String = "Az élet iskolája",
    val Birthplace : String = "Parlament",
    val Birthdate : String = "0. 0. 0.",
    val GuardiaName : String = "Isten",
    val GuardianPhone : String = "+36 1 441 4000",
    val RoomNumber : String = "-1",
    val Country : String = "Magyarország",
    val City : String = "Budapest",
    val Street : String = "Parlament utca (Földrajzból elírásból lettem 5-ös)",
    val PostCode : String = "A Parlament posta kódja",
    val Address : String = "0",
    val Floor : String = "100",
    val Door : String = "999",
){
    companion object {
        var instance: UserData = UserData()
    }

    init {
        instance = this
    }
}