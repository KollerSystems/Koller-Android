package com.norbert.koller.student.activities

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.PathInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.norbert.koller.shared.databinding.ActivityLoginBinding
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.student.R
import com.norbert.koller.student.databinding.ActivityAcquiredKeyBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random


class AcquiredKeyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAcquiredKeyBinding
    private val handler = Handler(Looper.getMainLooper())


    private fun animateFloatingView(view: View) {
        handler.post(object : Runnable {
            override fun run() {
                val maxOffset = 33

                val newX = Random.nextInt(-maxOffset, maxOffset).toFloat()
                val newY = Random.nextInt(-maxOffset, maxOffset).toFloat()
                val distance = (sqrt((newX.toDouble() - view.translationX).pow(2.0) + (newY.toDouble() - view.translationY).pow(2.0)) * 25).toLong()
                view.animate()
                    .translationX(newX)
                    .translationY(newY)
                    .setDuration(distance)
                    .start()

                handler.postDelayed(this, distance)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityAcquiredKeyBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.flBody) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        binding.uv.setUser(CacheManager.getCurrentUserData()!!)

        binding.pb.post {
            binding.pb.trackThickness = binding.root.height
            binding.pb2.trackThickness = binding.root.height
        }


        binding.stars.onStart()
        binding.btnClose.setOnClickListener {
            finish()
        }

        animateFloatingView(binding.lyTime)

        fun fadeSwitch() {
            if (binding.uv.isVisible) {
                binding.uv.animate().alpha(0f).setDuration(100).withEndAction {
                    binding.uv.visibility = View.GONE
                    binding.lyIcon.alpha = 0f
                    binding.lyIcon.visibility = View.VISIBLE
                    binding.lyIcon.animate().alpha(1f).setDuration(100)
                }

            } else {
                binding.lyIcon.animate().alpha(0f).setDuration(100).withEndAction {
                    binding.lyIcon.visibility = View.GONE
                    binding.uv.alpha = 0f
                    binding.uv.visibility = View.VISIBLE
                    binding.uv.animate().alpha(1f).setDuration(100)

                }
            }
            binding.pb.setProgress(Random.nextInt(0, 11), true)
            binding.pb2.setProgress(Random.nextInt(0, 11), true)
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    fadeSwitch()
                }
            }
        }, 1000, 1000)
    }

    companion object {
        const val TAG = "AcquiredKeyDFragment"
    }
}