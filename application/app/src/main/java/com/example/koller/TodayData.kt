package com.example.koller

import android.graphics.drawable.Drawable

data class TodayData(var read : Boolean, var iconLeft: Drawable?, var title: String, var description: String, var charRight: String, var iconRight: Drawable?)
{
    constructor(read : Boolean, iconLeft: Drawable?, title: String, description: String, grade: String) : this(read, iconLeft, title, description, grade, null)
    constructor(read : Boolean, iconLeft: Drawable?, title: String, description: String, icon: Drawable?) : this(read, iconLeft, title, description, "", icon)
    constructor(read : Boolean, iconLeft: Drawable?, title: String, description: String) : this(read, iconLeft, title, description, "", null)
    constructor(iconLeft: Drawable?, title: String, description: String, grade: String) : this(true, iconLeft, title, description, grade, null)
    constructor(iconLeft: Drawable?, title: String, description: String, icon: Drawable?) : this(true, iconLeft, title, description, "", icon)
    constructor(iconLeft: Drawable?, title: String, description: String) : this(true, iconLeft, title, description, "", null)
}