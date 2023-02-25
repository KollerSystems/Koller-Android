data class ApiLoginTokensData (
        val access_token : String,
        val token_type : String,
        val expires_in : Int,
        val refresh_token : String
){
        companion object {
                lateinit var instance: ApiLoginTokensData
        }

        init {
                instance = this
        }
}

