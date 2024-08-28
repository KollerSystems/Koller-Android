package com.norbert.koller.shared.customviews

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.norbert.koller.shared.databinding.ViewLoadingOverlayBinding
import com.norbert.koller.shared.viewmodels.DetailsViewModel

class LoadingOverlayView : LinearLayout {

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!,
        attrs,
        defStyle
    )

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
    constructor(context: Context?) : super(context!!)

    lateinit var binding : ViewLoadingOverlayBinding

    private var state: Int = DetailsViewModel.LOADING

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
            DetailsViewModel.LOADING ->{
                crossFade(binding.error.root, binding.progressBar)
            }
            DetailsViewModel.ERROR ->{
                crossFade(binding.progressBar, binding.error.root)
            }
            DetailsViewModel.NONE ->{
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
        binding = ViewLoadingOverlayBinding.inflate(LayoutInflater.from(context), this, true)

        binding.error.btn.setOnClickListener{
            setState(DetailsViewModel.LOADING)
            loadData!!.invoke()
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

}