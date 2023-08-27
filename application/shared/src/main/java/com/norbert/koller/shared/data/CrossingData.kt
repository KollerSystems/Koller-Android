package com.norbert.koller.shared.data

import java.sql.Timestamp


data class CrossingData(
    val ID : Int = -1,
    val PID : Int = -1,
    val Time : Timestamp,
    val Direction : Byte

)