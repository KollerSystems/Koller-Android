package com.norbert.koller.shared.fragments.bottomsheet.list

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.data.ListItem

class PhotoListBsdFragment : ListCardBsdfFragment() {

    var onFinish: ((fragment : Fragment, uri: Uri?) -> Unit)? = null




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var camUri : Uri? = null

        var startCamera : ActivityResultLauncher<Intent>? = null
        var startGallery : ActivityResultLauncher<Intent>? = null

        fun setupCamera(fragment: Fragment){
            startCamera = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    onFinish?.invoke(fragment, camUri)
                }
            }

            startGallery = fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    if (data != null) {
                        val uri: Uri? = data.data
                        if (uri != null) {
                            onFinish?.invoke(fragment, camUri)
                        }
                    }
                }
            }
        }

        setupCamera(this)

        getCardViewModel().list.value = arrayListOf<ListItem>(ListCardItem(getString(R.string.create_photo), null, AppCompatResources.getDrawable(requireContext(), R.drawable.camera), {

            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {

                ActivityCompat.requestPermissions(context as AppCompatActivity, arrayOf(Manifest.permission.CAMERA), 845)

            } else {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera")
                camUri = requireContext().contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camUri)

                startCamera!!.launch(cameraIntent)
            }
        }),
        ListCardItem(getString(R.string.select_photo), null, AppCompatResources.getDrawable(requireContext(), R.drawable.gallery), {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            startGallery!!.launch(galleryIntent)
        }))
    }



}