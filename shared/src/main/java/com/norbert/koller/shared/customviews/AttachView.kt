package com.norbert.koller.shared.customviews

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewAttachBinding
import com.norbert.koller.shared.fragments.bottomsheet.list.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.list.PhotoListBsdFragment
import com.norbert.koller.shared.managers.getAttributeColor
import com.stfalcon.imageviewer.StfalconImageViewer


class AttachView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    private lateinit var binding : ViewAttachBinding

    var mTitle : String = ""


    fun showDialog(){
        val fragmentManager = (context as AppCompatActivity)
        val dialog = PhotoListBsdFragment()
        dialog.show(fragmentManager.supportFragmentManager, ListBsdfFragment.TAG)
        dialog.onFinish = { fragment, uri -> addImage(fragment, uri)}
    }

    fun resetUI(){
        binding.image.setImageDrawable(null)
        binding.image.isVisible = false
        binding.textTip.isVisible = true
        binding.flBtns.isVisible = false
        binding.textTitle.background = null
        binding.textTitle.setTextColor(Color.WHITE)
        binding.textTitle.setShadowLayer(0f,0f,0f,0)

        binding.card.setOnClickListener{
            showDialog()
        }
    }

    fun addImage(fragment: Fragment, uri: Uri?){

        binding.image.setImageURI(uri)
        binding.image.isVisible = true
        binding.textTip.isVisible = false
        binding.flBtns.isVisible = true
        binding.textTitle.background = AppCompatResources.getDrawable(context, R.drawable.gradient_up_down)
        binding.textTitle.setTextColor(context.getAttributeColor(com.google.android.material.R.attr.colorOnSurface))
        binding.textTitle.setShadowLayer(1.5f, 0f, 0.75f, Color.parseColor("#80000000"))

        binding.card.setOnClickListener{
            StfalconImageViewer.Builder(context, listOf(binding.image.drawable)){ view, drawable ->
                view.setImageDrawable(drawable)
            }
                .withTransitionFrom(binding.image)
                .show(fragment.parentFragmentManager)
        }

        binding.btnChange.setOnClickListener{
            showDialog()
        }

        binding.btnRemove.setOnClickListener{
            MaterialAlertDialogBuilder(context)
                .setMessage("Biztos törli a képet?")
                .setPositiveButton(context.getString(R.string.remove)){_,_->
                    resetUI()
                }
                .setNegativeButton(context.getString(R.string.cancel)){_,_->

                }
                .show()

        }

    }

    init {

        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.attachView,
            0, 0
        )

        try {
            mTitle = typedArray.getString(R.styleable.attachView_title) ?: ""
        } finally {
            typedArray.recycle()
        }

        binding = ViewAttachBinding.inflate(LayoutInflater.from(context), this, true)

        binding.textTitle.text = mTitle


        binding.card.setOnClickListener{
           showDialog()
        }
    }



}