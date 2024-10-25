package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.data.OutgoingData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleStaticBsdfFragment
import com.norbert.koller.shared.helpers.connectToDatePicker
import com.norbert.koller.shared.helpers.connectToTimePicker
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.recycleradapters.ListCardItem
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListToggleItem
import com.norbert.koller.shared.viewmodels.DetailsViewModel
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.ContentActivityCreateOutgoingBinding

class CreateOutgoingActivity() : EditableToolbarActivity() {



    companion object{
        const val TEMPORARY : Int = 0
        const val PERMANENT : Int = 1
    }

    lateinit var binding : ContentActivityCreateOutgoingBinding

    lateinit var viewModel : DetailsViewModel

    fun outgoingData() : OutgoingData{
        return (viewModel.response.value as OutgoingData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]

        val uid = intent.getIntExtra("id", -1)
        val type = intent.getIntExtra("type", -1)

        if(savedInstanceState == null){
            val data = OutgoingData()
            if(uid != -1){
                data.addresses = arrayListOf(CacheManager.detailsDataMap[Pair(UserData::class.java.simpleName, uid)] as UserData)
            }
            viewModel.response.value = data

        }

        findViewById<AppBarLayout>(com.norbert.koller.shared.R.id.app_bar).setup()

        val textFirst : TextView = findViewById(R.id.text_title_first)
        if(textFirst.text.isNullOrBlank()){
            textFirst.visibility = GONE
        }

        val buttonExit : Button = findViewById(R.id.button_back)

        buttonExit.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tilDateFrom.connectToDatePicker(supportFragmentManager)
        binding.tilDateTo.connectToDatePicker(supportFragmentManager)
        binding.tilTimeFrom.connectToTimePicker(supportFragmentManager)
        binding.tilTimeTo.connectToTimePicker(supportFragmentManager)


        when (type){
            TEMPORARY ->{
                getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.temporary)
                getTitleTil().editText!!.tag = TEMPORARY
            }
            PERMANENT ->{
                getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.continuous)
                getTitleTil().editText!!.tag = PERMANENT
            }
        }

        getTitleTil().editText!!.setOnClickListener{

            currentFocus?.clearFocus()

            val dialog = ListCardStaticBsdfFragment().setup(this, arrayListOf(
                ListCardItem(
                    getString(com.norbert.koller.shared.R.string.temporary),
                    null,
                    AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.transparent_clock), {
                        getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.temporary)
                        getTitleTil().editText!!.tag = TEMPORARY
                    }),

                ListCardItem(
                    getString(com.norbert.koller.shared.R.string.continuous),
                    null,
                    AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.clock), {
                        getTitleTil().editText!!.setText(com.norbert.koller.shared.R.string.continuous)
                        getTitleTil().editText!!.tag = PERMANENT
                    })

            )).show(supportFragmentManager, ListBsdfFragment.TAG)

        }

        val onChange : (() -> Unit) = {
            getConfirmButton().isEnabled = (ApplicationManager.allFilled(binding.tilDateFrom, binding.tilDateTo, binding.tilTimeFrom, binding.tilTimeTo, binding.tilPurpose) && binding.tilAddresse.containsChip())
        }

        ApplicationManager.waitForChange(onChange, binding.tilDateFrom, binding.tilDateTo, binding.tilTimeFrom, binding.tilTimeTo, binding.tilPurpose)
        binding.tilAddresse.onChipChange = onChange

        onChange.invoke()


        viewModel.response.observe(this){
            for (user in outgoingData().addresses){
                binding.tilAddresse.addChip(user.name!!)
            }

        }

        onBackPressedDispatcher.addCallback(this){
            MaterialAlertDialogBuilder(this@CreateOutgoingActivity)
                .setTitle(getString(com.norbert.koller.shared.R.string.are_you_sure_you_want_to_discard_the_post))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.discard)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.cancel)) { _, _ ->

                }
                .setNeutralButton(getString(com.norbert.koller.shared.R.string.create_a_draft)){ _, _->
                    finish()
                }
                .show()
        }
    }

    override fun createContentView(): ViewGroup {
        binding = ContentActivityCreateOutgoingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun getName(): Pair<String, String> {
        return Pair(getString(com.norbert.koller.shared.R.string.create_new_x_first_part), getString(R.string.create_new_x_outgoing_last_part))
    }
}