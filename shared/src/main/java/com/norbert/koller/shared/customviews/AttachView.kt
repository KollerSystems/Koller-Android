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
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.norbert.koller.shared.R
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListStaticBsdFragment
import com.norbert.koller.shared.recycleradapters.ListItem
import com.stfalcon.imageviewer.StfalconImageViewer


class AttachView(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    var mTitle : String = ""

    val card : MaterialCardView
    val textTitle : TextView
    val textTip : TextView
    val imageView : ImageView
    val buttons : FlexboxLayout
    val buttonRemove : Button
    val buttonChange : Button

    var camUri : Uri? = null
    var startCamera : ActivityResultLauncher<Intent>? = null
    var startGallery : ActivityResultLauncher<Intent>? = null

    fun showDialog(){
        val fragmentManager = (context as AppCompatActivity)
        val dialog = ListStaticBsdFragment(arrayListOf(
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
        dialog.show(fragmentManager.supportFragmentManager, ListBsdFragment.TAG)
    }

    fun resetUI(){
        imageView.setImageDrawable(null)
        imageView.isVisible = false
        textTip.isVisible = true
        buttons.isVisible = false
        textTitle.background = null
        textTitle.setShadowLayer(0f,0f,0f,0)

        card.setOnClickListener{
            showDialog()
        }
    }

    fun addImage(fragment: Fragment, uri: Uri?){

            imageView.setImageURI(uri)
            imageView.isVisible = true
            textTip.isVisible = false
            buttons.isVisible = true
            textTitle.background = AppCompatResources.getDrawable(context, R.drawable.gradient_up_down)
            textTitle.setShadowLayer(1.5f, 0f, 0.75f, Color.parseColor("#80000000"))

            card.setOnClickListener{
                StfalconImageViewer.Builder(context, listOf(imageView.drawable)){ view, drawable ->
                    view.setImageDrawable(drawable)
                }
                    .withTransitionFrom(imageView)
                    .show(fragment.parentFragmentManager)
            }

            buttonChange.setOnClickListener{
                showDialog()
            }

            buttonRemove.setOnClickListener{
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

        View.inflate(context, R.layout.view_attach, this)

        card = findViewById(R.id.card)
        textTitle = findViewById(R.id.title)
        textTip = findViewById(R.id.text)
        imageView = findViewById(R.id.image)
        buttons = findViewById(R.id.fl_buttons)
        buttonRemove = findViewById(R.id.button_remove)
        buttonChange = findViewById(R.id.button_change)

        textTitle.text = mTitle








        card.setOnClickListener{
           showDialog()
        }
    }



}