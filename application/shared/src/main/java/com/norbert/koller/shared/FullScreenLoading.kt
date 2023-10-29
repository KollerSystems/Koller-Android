package com.norbert.koller.shared

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.norbert.koller.shared.fragments.RoomsFragment

class FullScreenLoading(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var state: Int = LOADING
    private val overlay : View
    private val loading : View
    private val error : View
    private val retry : Button
    lateinit var loadData: () -> Unit

    fun setState(value : Int){
        state = value
        when (state){
            LOADING->{
                error.visibility = GONE
                loading.visibility = VISIBLE
            }
            ERROR->{
                error.visibility = VISIBLE
                loading.visibility = GONE
            }
            NONE->{
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
        overlay.animate()
            .alpha(0.0f)
            .setDuration(300)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    overlay.visibility = View.GONE
                }
            })
    }

    init {
        View.inflate(context, R.layout.full_screen_loading, this)

        overlay = findViewById(R.id.overlay)
        loading = findViewById(R.id.progress_bar)
        error = findViewById(R.id.error)
        retry = findViewById(R.id.button)

        retry.setOnClickListener{
            setState(LOADING)
            loadData.invoke()
        }

        overlay.post{
            loadData.invoke()
        }
    }

}