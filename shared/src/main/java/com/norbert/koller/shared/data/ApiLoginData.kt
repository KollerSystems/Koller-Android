package com.norbert.koller.shared.data

abstract class ApiLoginData (
    val grant_type : String
)

class ApiLoginRefreshData(
    grant_type : String,
    val refresh_token : String
) : ApiLoginData(grant_type)

class ApiLoginUsernameAndPasswordData(
    grant_type : String,
    val username : String,
    val password : String
) : ApiLoginData(grant_type)
