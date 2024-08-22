package com.norbert.koller.shared.customviews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewFullScreenLoadingBinding
import com.norbert.koller.shared.viewmodels.ResponseViewModel
import com.stfalcon.imageviewer.common.extensions.isVisible

class FullScreenLoading : LinearLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    lateinit var binding : ViewFullScreenLoadingBinding

    private var state: Int = ResponseViewModel.LOADING

    var loadData: (() -> Unit)? = null

    fun crossFade(toHide : View, toShow : View){
        toHide.animate()
            .alpha(0.0f)
            .setDuration(100)

        toShow.visibility = VISIBLE
        toShow.animate()
            .alpha(1f)
            .setDuration(100)
    }

    fun setState(value : Int){
        state = value
        when (state){
            ResponseViewModel.LOADING ->{
                crossFade(binding.error.root, binding.progressBar)
            }
            ResponseViewModel.ERROR ->{
                crossFade(binding.progressBar, binding.error.root)
            }
            ResponseViewModel.NONE ->{
                fadeOut()
            }
        }
    }



    fun fadeOut(){
        binding.root.animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.root.visibility = View.GONE
                }
            })
    }

    init {
        binding = ViewFullScreenLoadingBinding.inflate(LayoutInflater.from(context), this, true)

        binding.error.btn.setOnClickListener{
            setState(ResponseViewModel.LOADING)
            loadData!!.invoke()
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

}