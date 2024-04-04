package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.R
import kotlinx.coroutines.launch


class TipView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var mText: String = ""
    private var mType: Int = 0


    val buttonClose: Button

    init {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.tipView,
            0, 0
        )

        try {
            mText = typedArray.getString(R.styleable.tipView_text) ?: ""
            mType = typedArray.getInt(R.styleable.tipView_type, 0)
        } finally {
            typedArray.recycle()
        }

        View.inflate(context, R.layout.view_tip, this)

        buttonClose = findViewById(R.id.button_close)

        (context as AppCompatActivity).lifecycleScope.launch {
            var read = 0
            val data = DataStoreManager.readInt(context, resources.getResourceEntryName(id))
            if(data != null) read = data
            if(read == -1 || (mType == 0 && read < 2)){
                visibility = GONE
            }
            else{
                val textText: TextView
                val textName: TextView
                val icon: ImageView

                textText = findViewById(R.id.text_view)
                textName = findViewById(R.id.text_name)
                icon = findViewById(R.id.image_icon)

                when (mType){
                    0->{

                    }
                    1->{
                        icon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.help))
                        textName.text = context.getString(R.string.help)
                    }
                }



                textText.text = mText

                buttonClose.setOnClickListener{
                    visibility = GONE
                    context.lifecycleScope.launch {
                        DataStoreManager.save(context, resources.getResourceEntryName(id), -1)
                    }
                }
            }
            if(read != -1){
                read++
                DataStoreManager.save(context, resources.getResourceEntryName(id), read)
            }
        }


    }

}