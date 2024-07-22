package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.viewmodels.ListApiBsdFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class ListApiBsdFragment(var apiToCall : (suspend () -> Response<*>)? = null, alreadyChecked : ArrayList<String>? = null, var key : String? = null) : ListBsdFragment(alreadyChecked) {


    fun apiViewModel() : ListApiBsdFragmentViewModel{
        return viewModel as ListApiBsdFragmentViewModel
    }

    var progressBar : ProgressBar? = null





    override fun toggleList(): Boolean {
        return viewModel.getValuesOnFinish != null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[ListApiBsdFragmentViewModel::class.java]

        if(savedInstanceState == null){
            apiViewModel().key = key!!

            if(CacheManager.savedListsOfValues.containsKey(apiViewModel().key)){
                viewModel.list.value = apiViewModel().responseToListItemList(CacheManager.savedListsOfValues[apiViewModel().key]!!)
            }
            else{
                lifecycleScope.launch {
                    apiViewModel().call(apiToCall!!)
                }
            }

        }

        super.onViewCreated(view, savedInstanceState)




        if(!viewModel.list.isInitialized){
            progressBar = ProgressBar(requireContext())
            val margin = requireContext().resources.getDimensionPixelSize(R.dimen.text_container_margin)
            val mlp = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            mlp.setMargins(margin,margin,margin,margin)
            progressBar!!.layoutParams = mlp
            (recycleView.parent as ViewGroup).addView(progressBar)
        }

        viewModel.list.observe(this){
            if(it == null){
                dismiss()
                var snackbar = (requireContext() as MainActivity).getSnackBar(getString(R.string.an_error_occurred), Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            else{
                if(progressBar != null){
                    (recycleView.parent as ViewGroup).removeView(progressBar)
                }
                setRecyclerView()
            }
        }



    }

}