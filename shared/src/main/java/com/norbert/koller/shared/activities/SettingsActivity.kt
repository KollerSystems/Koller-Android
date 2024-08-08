package com.norbert.koller.shared.activities

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View.VISIBLE
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.CardToggle
import com.norbert.koller.shared.api.APIInterface
import com.google.android.material.checkbox.MaterialCheckBox
import com.norbert.koller.shared.databinding.ContentActivitySettingsDeveloperBinding
import com.norbert.koller.shared.databinding.ContentActivitySettingsExternalBinding
import java.util.Calendar

abstract class SettingsActivity : ToolbarActivity() {

    abstract fun getExternalBinding() : ContentActivitySettingsExternalBinding
    abstract fun getDeveloperBinding() : ContentActivitySettingsDeveloperBinding

    override fun getName(): String {
        return getString(R.string.settings)
    }

    companion object {
        var timeOffset : Float = 0f
    }

    var isUpdatingChildren = false

    fun setParentState(
        checkBoxParent: CardToggle,
        childrenCheckBoxes: List<CardToggle>,
        parentOnCheckedStateChangedListener: MaterialCheckBox.OnCheckedStateChangedListener
    ) {
        val checkedCount = childrenCheckBoxes.stream().filter { obj: CardToggle -> obj.getCheckBox().isChecked }
            .count()
            .toInt()
        val allChecked = checkedCount == childrenCheckBoxes.size
        val noneChecked = checkedCount == 0
        checkBoxParent.getCheckBox().removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.getCheckBox().isChecked = true
        } else if (noneChecked) {
            checkBoxParent.getCheckBox().isChecked = false
        } else {
            checkBoxParent.getCheckBox().checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.getCheckBox().addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        getDeveloperBinding().sliderListLoadingDelay.setValues(APIInterface.loadingDelayFrom, APIInterface.loadingDelayTo)

        getDeveloperBinding().sliderListLoadingDelay.addOnChangeListener{ slider, value, fromUser ->

            APIInterface.loadingDelayFrom = slider.values[0]
            APIInterface.loadingDelayTo = slider.values[1]

        }

        val c : Calendar = Calendar.getInstance()
        val hours : Float = (c.get(Calendar.SECOND)  / 60f / 60f+ c.get(Calendar.MINUTE) / 60f + c.get(Calendar.HOUR_OF_DAY))
        getDeveloperBinding().buttonTimeOffsetReset.text = (hours + timeOffset).toString()

        getDeveloperBinding().sliderTimeOffset.valueFrom = hours * -1
        getDeveloperBinding().sliderTimeOffset.valueTo = (hours - 24) *-1

        getDeveloperBinding().buttonTimeOffsetReset.setOnClickListener{
            getDeveloperBinding().sliderTimeOffset.value = 0f
        }

        getDeveloperBinding().sliderTimeOffset.value = timeOffset

        getDeveloperBinding().sliderTimeOffset.addOnChangeListener { slider, value, fromUser ->
            timeOffset = value
            getDeveloperBinding().buttonTimeOffsetReset.text = (hours + timeOffset).toString()
        }


        getExternalBinding().cbNotification.setOnClickListener {
            val notificationsIntent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(notificationsIntent)
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getExternalBinding().cbLanguage.visibility = VISIBLE
            getExternalBinding().cbLanguage.setOnClickListener {
                val languageIntent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                languageIntent.data = Uri.fromParts("package", packageName, null)
                startActivity(languageIntent)
            }
        }


    }
}