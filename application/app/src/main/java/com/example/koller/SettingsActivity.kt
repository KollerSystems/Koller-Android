package com.example.koller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.ArrayList

class SettingsActivity : AppCompatActivity() {

    private var isUpdatingChildren = false

    private fun setParentState(
        checkBoxParent: MaterialCheckBox,
        childrenCheckBoxes: List<CheckBox>,
        parentOnCheckedStateChangedListener: MaterialCheckBox.OnCheckedStateChangedListener
    ) {
        val checkedCount = childrenCheckBoxes.stream().filter { obj: CheckBox -> obj.isChecked }
            .count()
            .toInt()
        val allChecked = checkedCount == childrenCheckBoxes.size
        val noneChecked = checkedCount == 0
        checkBoxParent.removeOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
        if (allChecked) {
            checkBoxParent.isChecked = true
        } else if (noneChecked) {
            checkBoxParent.isChecked = false
        } else {
            checkBoxParent.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val checkBoxParent: MaterialCheckBox = findViewById(R.id.notifics_all)
        val childrenCheckBoxes: ArrayList<MaterialCheckBox> = arrayListOf(findViewById(R.id.notifics_arrival), findViewById(R.id.notifics_new), findViewById(R.id.notifics_room), findViewById(R.id.notifics_occupation), findViewById(R.id.notifics_comm_or_warn))

        // Parent's checked state changed listener
        val parentOnCheckedStateChangedListener =
            MaterialCheckBox.OnCheckedStateChangedListener { checkBox: MaterialCheckBox, state: Int ->
                val isChecked = checkBox.isChecked
                if (state != MaterialCheckBox.STATE_INDETERMINATE) {
                    isUpdatingChildren = true
                    for (child in childrenCheckBoxes) {
                        child.isChecked = isChecked
                    }
                    isUpdatingChildren = false
                }
            }

        checkBoxParent.addOnCheckedStateChangedListener(parentOnCheckedStateChangedListener)

        // Checked state changed listener for each child
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
            (child as MaterialCheckBox)
                .addOnCheckedStateChangedListener(childOnCheckedStateChangedListener)
        }
    }
}