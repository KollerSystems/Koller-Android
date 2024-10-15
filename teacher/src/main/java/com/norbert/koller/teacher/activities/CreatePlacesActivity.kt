package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.data.OutgoingData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdfFragment
import com.norbert.koller.shared.helpers.connectToDatePicker
import com.norbert.koller.shared.helpers.connectToTimePicker
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.activities.CreateOutgoingActivity.Companion.PERMANENT
import com.norbert.koller.teacher.activities.CreateOutgoingActivity.Companion.TEMPORARY
import com.norbert.koller.teacher.databinding.ActivityCreatePlacesBinding
import com.norbert.koller.teacher.databinding.ContentActivityCreateOutgoingBinding

class CreatePlacesActivity : EditableToolbarActivity() {

    companion object{
        const val ROOM : Int = 0
    }

    lateinit var binding : ActivityCreatePlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = intent.getIntExtra("type", -1)

        findViewById<AppBarLayout>(com.norbert.koller.shared.R.id.app_bar).setup()

        val textFirst : TextView = findViewById(R.id.text_title_first)
        if(textFirst.text.isNullOrBlank()){
            textFirst.visibility = GONE
        }

        val buttonExit : Button = findViewById(R.id.button_back)

        buttonExit.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        when (type){
            ROOM ->{
                getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.room)
                getTitleTil().editText!!.tag = ROOM
            }
        }

        getTitleTil().editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dialog = ListStaticBsdfFragment(arrayListOf(
                ListItem(
                    getString(com.norbert.koller.shared.R.string.room),
                    null,
                    AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.room),
                    null, {
                        getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.room)
                        getTitleTil().editText!!.tag = ROOM
                    })

            ))
            dialog.show(supportFragmentManager, ListBsdfFragment.TAG)

        }

        val onChange : (() -> Unit) = {
            getConfirmButton().isEnabled = ApplicationManager.allFilled(binding.tilFloor, binding.tilAnnexe, binding.tilFromNumber)
        }

        ApplicationManager.waitForChange(onChange, binding.tilFloor, binding.tilAnnexe, binding.tilFromNumber)

        onChange.invoke()

        onBackPressedDispatcher.addCallback(this){
            MaterialAlertDialogBuilder(this@CreatePlacesActivity)
                .setTitle(getString(com.norbert.koller.shared.R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.discard)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.cancel)) { _, _ ->

                }
                .show()
        }

        displayButton(getString(com.norbert.koller.shared.R.string.create), com.norbert.koller.shared.R.drawable.check)
    }

    override fun createContentView(): ViewGroup {
        binding = ActivityCreatePlacesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): Pair<String, String> {
        return Pair(getString(com.norbert.koller.shared.R.string.create_new_x_first_part), getString(
            com.norbert.koller.shared.R.string.create_new_x_last_part))
    }
}