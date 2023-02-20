package com.example.koller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.google.android.material.checkbox.MaterialCheckBox
import java.util.ArrayList

class SettingsFragment : Fragment() {

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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_settings, container, false)

        val checkBoxParent: MaterialCheckBox = view.findViewById(R.id.notifics_all)
        val childrenCheckBoxes: ArrayList<MaterialCheckBox> = arrayListOf(view.findViewById(R.id.notifics_arrival), view.findViewById(R.id.notifics_new), view.findViewById(R.id.notifics_room), view.findViewById(R.id.notifics_occupation), view.findViewById(R.id.notifics_comm_or_warn))

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

        return view
    }
}