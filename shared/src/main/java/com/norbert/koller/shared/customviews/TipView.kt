package com.norbert.koller.shared.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewTipBinding
import com.norbert.koller.shared.managers.DataStoreManager.Companion.TOKENS
import com.norbert.koller.shared.managers.DataStoreManager.Companion.loginDataStore
import com.norbert.koller.shared.managers.DataStoreManager.Companion.tipDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class TipView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    val binding = ViewTipBinding.inflate(LayoutInflater.from(context), this, true)

    private var mText: String = ""
    private var mType: Int = 0

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

        (context as AppCompatActivity).lifecycleScope.launch {
            var read = 0
            val data = context.tipDataStore.data.first()[intPreferencesKey(resources.getResourceEntryName(id))]
            if(data != null) read = data
            if(read == -1 || (mType == 0 && read < 2)){
                visibility = GONE
            }
            else{
                when (mType){
                    0->{

                    }
                    1->{
                        binding.imageIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.help))
                        binding.textLabel.text = context.getString(R.string.help)
                    }
                }

                binding.text.text = mText

                binding.buttonClose.setOnClickListener{
                    visibility = GONE
                    context.lifecycleScope.launch {
                        context.tipDataStore.edit {
                            it[intPreferencesKey(resources.getResourceEntryName(id))] = -1
                        }
                    }
                }
            }
            if(read != -1){
                read++
                context.tipDataStore.edit {
                    it[intPreferencesKey(resources.getResourceEntryName(id))] = read
                }
            }
        }


    }

}