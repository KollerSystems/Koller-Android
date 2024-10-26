package com.norbert.koller.shared.data

import android.graphics.drawable.Drawable

class ListCardItem(title: String, description: String? = null, icon: Drawable? = null, val function: (() -> Unit)? = null) : ListItem(title, description, icon)