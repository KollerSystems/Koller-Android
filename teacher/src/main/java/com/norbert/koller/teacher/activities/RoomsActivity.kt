package com.norbert.koller.teacher.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.connectToBackgroundCard
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.recycleradapter.ListItem
import com.norbert.koller.shared.setToolbarToViewColor
import com.norbert.koller.teacher.R

abstract class RoomsActivity : AppCompatActivity() {

    lateinit var viewPager : ViewPager2
    lateinit var tabLayout : TabLayout

    lateinit var buttonPublishAll : Button
    lateinit var buttonMore : Button

    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout

    override fun onBackPressed() {

        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.are_you_sure_discard_all_grade))
            .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->
                super.onBackPressed()
            }
            .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

            }
            .setNeutralButton(getString(com.norbert.koller.shared.R.string.create_a_draft)){ _, _ ->
                super.onBackPressed()
            }
            .show()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)

        viewPager  = findViewById(R.id.view_pager)
        tabLayout = findViewById(R.id.tab_layout)
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout)


        window.setToolbarToViewColor(findViewById(R.id.bottom_view))


        val mainBackground : MaterialCardView = findViewById(R.id.main_background)
        val appBar : AppBarLayout = findViewById(R.id.appbar)

        appBar.connectToBackgroundCard(mainBackground)

        val backButton : Button = findViewById(R.id.toolbar_exit)

        backButton.setOnClickListener{
            onBackPressed()
        }

        buttonPublishAll = findViewById(R.id.button_publish_all)
        buttonMore = findViewById(R.id.button_more)

        buttonPublishAll.setOnClickListener{
            MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.are_you_sure_publish_all_grade))
                .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->
                    finish()
                }
                .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

                }
                .show()
        }

        buttonMore.setOnClickListener{
            val dialog = ItemListDialogFragment(arrayListOf(
                ListItem(getString(com.norbert.koller.shared.R.string.delete_all), null, AppCompatResources.getDrawable(this, com.norbert.koller.shared.R.drawable.close), null, {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.are_you_sure_delete_all_grade))
                        .setPositiveButton(getString(com.norbert.koller.shared.R.string.yes)) { _, _ ->

                        }
                        .setNegativeButton(getString(com.norbert.koller.shared.R.string.no)){ _, _ ->

                        }
                        .show()
                })))
            dialog.show(supportFragmentManager, ItemListDialogFragment.TAG)

        }

    }

}