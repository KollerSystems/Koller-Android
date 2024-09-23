package com.norbert.koller.shared.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilterConfigData(
    var filters : MutableMap<String, ArrayList<String>>
) : Parcelable