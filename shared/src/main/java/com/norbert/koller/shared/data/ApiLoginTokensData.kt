package com.norbert.koller.shared.data

data class ApiLoginTokensData (
        val access_token : String = "",
        val token_type : String = "",
        val expires_in : Int = -1,
        val refresh_token : String = ""
){
        companion object {
                var instance = ApiLoginTokensData()
        }
}

