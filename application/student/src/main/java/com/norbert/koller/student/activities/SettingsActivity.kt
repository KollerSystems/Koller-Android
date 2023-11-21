package com.norbert.koller.student.activities

import android.os.Bundle
import com.norbert.koller.student.R
import com.norbert.koller.shared.customview.SimpleCardButtonWithToggle
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.*

class SettingsActivity : com.norbert.koller.shared.activities.SettingsActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_settings)
        super.onCreate(savedInstanceState)

        val checkBoxNotifArrival : SimpleCardButtonWithToggle = findViewById(R.id.notifics_arrival)
        val checkBoxNotifRoom : SimpleCardButtonWithToggle = findViewById(R.id.notifics_room)
        val checkBoxNotifOccupation : SimpleCardButtonWithToggle = findViewById(R.id.notifics_occupation)
        val checkBoxNotifCommOrWarn : SimpleCardButtonWithToggle = findViewById(R.id.notifics_comm_or_warn)

        val checkBoxParent: SimpleCardButtonWithToggle = findViewById(R.id.notifics_all)
        val childrenCheckBoxes: ArrayList<SimpleCardButtonWithToggle> = arrayListOf(
            checkBoxNotifArrival,
            checkBoxNotifRoom,
            checkBoxNotifOccupation,
            checkBoxNotifCommOrWarn)

        // Parent's checked state changed listener
        val parentOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox, state: Int ->
                val isChecked = checkBox.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.checkBox.isChecked = isChecked
                    }
                    isUpdatingChildren = false
                }
            }

        checkBoxParent.checkBox.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)


        val childOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox?, state: Int ->
                if (!isUpdatingChildren) {
                    setParentState(
                        checkBoxParent,
                        childrenCheckBoxes,
                        parentOnCheckedStateChangedListener
                    )
                }
            }
        for (child in childrenCheckBoxes) {
            (child.checkBox)
                .addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
    }

}