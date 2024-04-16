package com.norbert.koller.teacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.customviews.TextInputWithChips
import com.norbert.koller.shared.data.OutgoingData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentBase
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentStatic
import com.norbert.koller.shared.helpers.connectToDatePicker
import com.norbert.koller.shared.helpers.connectToTimePicker
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.norbert.koller.teacher.R

class CreateOutgoingActivity() : AppCompatActivity() {



    companion object{
        const val TEMPORARY : Int = 0
        const val PERMANENT : Int = 1

        var type : Int = 0
        var userData: UserData? = null
    }

    private lateinit var tilDateFrom : TextInputLayout
    private lateinit var tilDateTo : TextInputLayout
    private lateinit var tilTimeFrom : TextInputLayout
    private lateinit var tilTimeTo : TextInputLayout
    private lateinit var tilTitle: TextInputLayout
    private lateinit var tilAddresse: TextInputWithChips

    lateinit var viewModel : ResponseViewModel

    fun outgoingData() : OutgoingData{
        return (viewModel.response.value as OutgoingData)
    }

    override fun onBackPressed() {
        if(true){
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(com.norbert.koller.shared.R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->
                    super.onBackPressed()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)) { _, _ ->

                }
                .setNeutralButton(getString(com.norbert.koller.shared.R.string.create_a_draft)){ _, _->
                    super.onBackPressed()
                }
                .show()
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_outgoing)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]

        if(savedInstanceState == null){
            val data = OutgoingData()
            if(userData != null){
                data.addresses = arrayListOf(userData!!)
            }
            viewModel.response.value = data

        }

        findViewById<AppBarLayout>(com.norbert.koller.shared.R.id.appbar).setup()

        val textFirst : TextView = findViewById(R.id.text_first)
        if(textFirst.text.isNullOrBlank()){
            textFirst.visibility = GONE
        }

        val buttonExit : Button = findViewById(R.id.toolbar_exit)

        buttonExit.setOnClickListener{
            onBackPressed()
        }


        tilDateFrom = findViewById(com.norbert.koller.shared.R.id.create_new_post_til_date_from)
        tilDateTo = findViewById(com.norbert.koller.shared.R.id.create_new_post_til_date_to)
        tilTimeFrom = findViewById(com.norbert.koller.shared.R.id.create_new_post_til_time_from)
        tilTimeTo = findViewById(com.norbert.koller.shared.R.id.create_new_post_til_time_to)
        tilAddresse = findViewById (R.id.til_addresse)
        tilTitle = findViewById (R.id.create_new_post_til_title)

        tilDateFrom.connectToDatePicker(supportFragmentManager)
        tilDateTo.connectToDatePicker(supportFragmentManager)
        tilTimeFrom.connectToTimePicker(supportFragmentManager)
        tilTimeTo.connectToTimePicker(supportFragmentManager)

        val tilType: TextInputLayout = findViewById(com.norbert.koller.shared.R.id.create_new_post_til_type)


        when (type){
            TEMPORARY ->{
                tilType.editText!!.setText(com.norbert.koller.shared.R.string.temporary)
                tilType.editText!!.tag = TEMPORARY
            }
            PERMANENT ->{
                tilType.editText!!.setText(com.norbert.koller.shared.R.string.continuous)
                tilType.editText!!.tag = PERMANENT
            }
        }

        tilType.editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dialog = ItemListDialogFragmentStatic(arrayListOf(
                ListItem(
                    getString(com.norbert.koller.shared.R.string.temporary),
                    null,
                    AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.transparent_clock),
                    null, {
                        tilType.editText!!.setText(com.norbert.koller.shared.R.string.temporary)
                        tilType.editText!!.tag = TEMPORARY
                    }),

                ListItem(
                    getString(com.norbert.koller.shared.R.string.continuous),
                    null,
                    AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.clock),
                    null, {
                        tilType.editText!!.setText(com.norbert.koller.shared.R.string.continuous)
                        tilType.editText!!.tag = PERMANENT
                    })

            ))
            dialog.show(supportFragmentManager, ItemListDialogFragmentBase.TAG)

        }

        val publishButton: Button = findViewById (com.norbert.koller.shared.R.id.create_new_post_button_publish)

        val onChange : (() -> Unit) = {
            publishButton.isEnabled = (ApplicationManager.allFilled(tilDateFrom, tilDateTo, tilTimeFrom, tilTimeTo, tilTitle) && tilAddresse.containsChip())
        }

        ApplicationManager.waitForChange(onChange, tilDateFrom, tilDateTo, tilTimeFrom, tilTimeTo, tilTitle)
        tilAddresse.onChipChange = onChange

        onChange.invoke()


        viewModel.response.observe(this){
            for (user in outgoingData().addresses){
                tilAddresse.addChip(user.name!!)
            }

        }
    }
}