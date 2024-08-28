package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.viewmodels.ListApiBsdfFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class ListApiBsdfFragment(var apiToCall : (suspend () -> Response<*>)? = null, alreadyChecked : ArrayList<String>? = null, var key : String? = null, private val filterName : Int? = null) : ListBsdfFragment(alreadyChecked, filterName) {


    fun apiViewModel() : ListApiBsdfFragmentViewModel{
        return viewModel as ListApiBsdfFragmentViewModel
    }

    var itemLoading : ViewGroup? = null

    override fun toggleList(): Boolean {
        return viewModel.getValuesOnFinish != null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[ListApiBsdfFragmentViewModel::class.java]

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
            itemLoading = ItemLoadingBinding.inflate(layoutInflater).root
            getRoot().addView(itemLoading)
        }

        viewModel.list.observe(this){
            if(it == null){
                dismiss()
                var snackbar = (requireContext() as MainActivity).getSnackBar(getString(R.string.an_error_occurred), Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            else{
                if(itemLoading != null){
                    getRoot().removeView(itemLoading)
                }
                setRecyclerView()
            }
        }
    }

}