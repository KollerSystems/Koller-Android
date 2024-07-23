package com.norbert.koller.shared.customviews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.databinding.ViewFullScreenLoadingBinding

class FullScreenLoading : LinearLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    lateinit var binding : ViewFullScreenLoadingBinding

    private var state: Int = LOADING

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
            LOADING ->{
                crossFade(binding.error.root, binding.progressBar)
            }
            ERROR ->{
                crossFade(binding.progressBar, binding.error.root)
            }
            NONE ->{
                fadeOut()
            }
        }
    }

    companion object {
        const val LOADING = 0
        const val ERROR = 1
        const val NONE = 2
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
            setState(LOADING)
            loadData!!.invoke()
        }

        binding.root.post{
            if(loadData != null) {
                loadData!!.invoke()
            }
            else{
                binding.root.visibility = View.GONE
            }
        }
    }

}