package com.example.koller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.CheckBox
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.checkbox.MaterialCheckBox


class ProfileBottomSheet : BottomSheetDialogFragment() {

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
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_sheet_profile, container, false)

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

        val spinner : Spinner = view.findViewById(R.id.theme_mode)
        spinner.setSelection(0,false)

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(position == 0){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                else if(position == 1){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        return view
    }



    fun onItemSelected(
        parentView: AdapterView<*>?,
        selectedItemView: View?,
        position: Int,
        id: Long
    ) {
        // your code here
    }

    companion object {
        const val TAG = "ProfileBottomSheet"
    }
}