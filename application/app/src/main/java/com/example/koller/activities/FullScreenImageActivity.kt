package com.example.koller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.koller.R

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        postponeEnterTransition()

        var imageView : ImageView = findViewById(R.id.full_screen_image_image)
        var imageViewContainer : FrameLayout = findViewById(R.id.full_screen_image_frame_layout)

        imageView.setImageDrawable(getDrawable(R.drawable.sh_room))
        imageView.transitionName = "smooth_transition"
        startPostponedEnterTransition()
    }
}