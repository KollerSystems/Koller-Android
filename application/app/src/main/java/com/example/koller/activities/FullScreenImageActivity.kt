package com.example.koller.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.koller.R
import com.igreenwood.loupe.Loupe

class FullScreenImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)

        postponeEnterTransition()

        var imageView : ImageView = findViewById(R.id.full_screen_image_image)
        var imageViewContainer : FrameLayout = findViewById(R.id.full_screen_image_frame_layout)

        imageView.transitionName = "smooth_transition"
        startPostponedEnterTransition()

        val loupe = Loupe.create(imageView, imageViewContainer) { // imageView is your ImageView
            useFlingToDismissGesture = false
            onViewTranslateListener = object : Loupe.OnViewTranslateListener {

                override fun onStart(view: ImageView) {
                    // called when the view starts moving
                }

                override fun onViewTranslate(view: ImageView, amount: Float) {
                    // called whenever the view position changed
                }

                override fun onRestore(view: ImageView) {
                    // called when the view drag gesture ended
                }

                override fun onDismiss(view: ImageView) {
                    // called when the view drag gesture ended
                    finishAfterTransition()
                }
            }
        }
    }
}