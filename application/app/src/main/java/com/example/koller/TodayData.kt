package com.example.koller

import android.graphics.drawable.Drawable

data class TodayData(var title: String, var description: String, var grade: String, var icon: Drawable?) {
    constructor(title: String, description: String, grade: String) : this(title, description, grade, null)
    constructor(title: String, description: String, icon: Drawable?) : this(title, description, "", icon)
}