package com.norbert.koller.shared.data

import java.util.Date

data class EventsData (var title : String,
                       var description : String,
                       var imageURL : String,
                       var place : String? = null,
                       var dateTime : Date? = null,
                       var baseProgramReplacement : Int = 0)