package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName

class ContactsData(
    @SerializedName("ID") val id : Int = -1,
    @SerializedName("Discord") val discord : String? = null,
    @SerializedName("Facebook") val facebook : String? = null,
    @SerializedName("Instagram") val instagram : String? = null,
    @SerializedName("Email") val email : String? = null)