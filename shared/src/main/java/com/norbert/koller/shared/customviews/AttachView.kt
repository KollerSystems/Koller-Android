package com.norbert.koller.shared.customviews

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewAttachBinding
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdfFragment
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.recycleradapters.ListItem
import com.stfalcon.imageviewer.StfalconImageViewer


class AttachView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    private lateinit var binding : ViewAttachBinding

    var mTitle : String = ""

    var camUri : Uri? = null
    var startCamera : ActivityResultLauncher<Intent>? = null
    var startGallery : ActivityResultLauncher<Intent>? = null

    fun showDialog(){
        val fragmentManager = (context as AppCompatActivity)
        val dialog = ListStaticBsdfFragment(arrayListOf(
            ListItem("Fénykép készítése", null, AppCompatResources.getDrawable(context, R.drawable.camera), null, {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

                    ActivityCompat.requestPermissions(context as AppCompatActivity, arrayOf(Manifest.permission.CAMERA), 845)

                } else {
                    val values = ContentValues()
                    values.put(MediaStore.Images.Media.TITLE, "New Picture")
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
                    camUri = context.contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camUri)

                    startCamera!!.launch(cameraIntent)
                }
            }),
            ListItem("Fénykép kiválasztása", null, AppCompatResources.getDrawable(context, R.drawable.gallery), null, {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                startGallery!!.launch(galleryIntent)
            })
        ))
        dialog.show(fragmentManager.supportFragmentManager, ListBsdfFragment.TAG)
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
                .setTitle("Biztos törli a képet?")
                .setPositiveButton(context.getString(R.string.remove)){_,_->
                    resetUI()
                }
                .setNegativeButton(context.getString(R.string.cancel)){_,_->

                }
                .show()

        }

    }

    fun setupCamera(fragment: Fragment){
        startCamera = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                addImage(fragment, camUri)
            }
        }

        startGallery = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val uri: Uri? = data.data
                    if (uri != null) {
                        addImage(fragment, uri)

                    }
                }
            }
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