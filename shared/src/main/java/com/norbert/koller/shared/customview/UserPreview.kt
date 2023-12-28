package com.norbert.koller.shared.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.DataStoreManager
import com.norbert.koller.shared.R
import kotlinx.coroutines.launch

class UserPreview (context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    //TODO: Recycler view átvariálása ide

    val userBadge : RoundedBadgeImageView
    val text : TextView

    init {
        View.inflate(context, R.layout.view_tip, this)
        userBadge = findViewById(R.id.button_close)
        text = findViewById(R.id.text_name)
    }

}