package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.widget.Button
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.norbert.koller.shared.managers.setup
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdFragment
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.databinding.ActivityRoomsBinding

abstract class RoomsActivity : AppCompatActivity() {

    lateinit var contentBinding : ActivityRoomsBinding

    fun setTitle(title : String){
        contentBinding.toolbar.title = title
    }

    fun getViewPager() : ViewPager2{
        return contentBinding.viewPager
    }

    fun getTabLayout() : TabLayout{
        return contentBinding.tabLayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contentBinding = ActivityRoomsBinding.inflate(layoutInflater)
        setContentView(contentBinding.root)

        val callback = this.onBackPressedDispatcher.addCallback(
            owner = this,
            enabled = true
        ){
            MaterialAlertDialogBuilder(this@RoomsActivity)
                .setTitle(getString(R.string.are_you_sure_discard_all_grade))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->

                    finish()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

                }
                .setNeutralButton(getString(com.norbert.koller.shared.R.string.create_a_draft)){ _, _ ->
                    finish()
                }
                .show()
        }

        val appBar : AppBarLayout = findViewById(R.id.app_bar)

        appBar.setup()

        val backButton : Button = findViewById(R.id.button_back)

        backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }


        contentBinding.manageBar.button.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_publish_all_grade))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

                }
                .show()
        }

        /*buttonMore.setOnClickListener{
            val dialog = ListStaticBsdFragment(arrayListOf(
                ListItem(getString(com.norbert.koller.shared.R.string.delete_all), null, AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.close), null, {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.are_you_sure_delete_all_grade))
                        .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->

                        }
                        .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

                        }
                        .show()
                })))
            dialog.show(supportFragmentManager, ListBsdFragment.TAG)
        }*/
    }
}