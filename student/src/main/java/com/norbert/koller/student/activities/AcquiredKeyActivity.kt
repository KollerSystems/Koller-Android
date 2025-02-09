package com.norbert.koller.student.activities

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
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


class AcquiredKeyActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAcquiredKeyBinding

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

        binding.stars.onStart()
        binding.btnClose.setOnClickListener {
            finish()
        }

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