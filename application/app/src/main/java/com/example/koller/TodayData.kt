package com.example.koller

import android.graphics.drawable.Drawable

data class TodayData(var iconLeft: Drawable?, var title: String, var description: String, var charRight: String, var iconRight: Drawable?)
{
    constructor(iconLeft: Drawable?, title: String, description: String, grade: String) : this(iconLeft, title, description, grade, null)
    constructor(iconLeft: Drawable?, title: String, description: String, icon: Drawable?) : this(iconLeft, title, description, "", icon)
    constructor(iconLeft: Drawable?, title: String, description: String) : this(iconLeft, title, description, "", null)
}