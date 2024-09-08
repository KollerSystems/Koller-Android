package com.norbert.koller.shared.helpers

import android.content.Context
import android.view.ViewTreeObserver
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.recycleradapters.PagingSource

class ApiHelper {
    companion object {

        const val STATE_NONE = 0
        const val STATE_LOADING = 1
        const val STATE_ERROR = 2

        fun createSnackBar(context : Context, text : String, onRefresh: (()-> Unit)) : Snackbar{
            val snackbar = (context as MainActivity).getSnackBar(text, Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction(context.getString(R.string.retry)){
                onRefresh.invoke()
            }
            snackbar.view.viewTreeObserver
                .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        snackbar.view.viewTreeObserver.removeOnPreDrawListener(this)
                        if(snackbar.view.layoutParams is CoordinatorLayout.LayoutParams){
                            (snackbar.view.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
                        }
                        return true
                    }
                })
            snackbar.show()

            return snackbar
        }

        fun createSnackBar(context : Context, onRefresh: (()-> Unit)) : Snackbar{
            val snackbar = createSnackBar(context,  context.getString(R.string.failed_to_refresh), onRefresh)

            return snackbar
        }
    }
}