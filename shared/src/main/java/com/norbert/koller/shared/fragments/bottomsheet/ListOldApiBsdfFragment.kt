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
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.viewmodels.ListApiBsdfFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class ListOldApiBsdfFragment(var apiToCall : (suspend () -> Response<*>)? = null, alreadyChecked : ArrayList<String>? = null, var classOfT: Class<*>? = null, private val filterName : Int? = null) : ListBsdfFragment(alreadyChecked, filterName) {


    fun apiViewModel() : ListApiBsdfFragmentViewModel{
        return viewModel as ListApiBsdfFragmentViewModel
    }

    var itemLoading : ViewGroup? = null

    override fun toggleList(): Boolean {
        return viewModel.getValuesOnFinish != null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[ListApiBsdfFragmentViewModel::class.java]

        if (savedInstanceState == null) {
            apiViewModel().classOfT = classOfT!!

            lifecycleScope.launch {
                if (CacheManager.listDataMap.containsKey(apiViewModel().classOfT.simpleName)) {
                    viewModel.list.value = apiViewModel().responseToListItemList(CacheManager.getListDataMapWithValues(requireContext(), classOfT!!))
                } else {
                    val baseDataList = DataStoreManager.readList(requireContext(), classOfT!!)

                    if (baseDataList != null) {

                        if(baseDataList.isValid(requireContext(), DateTimeHelper.TIME_NOT_IMPORTANT)){
                            CacheManager.listDataMap[classOfT!!.simpleName] = baseDataList
                            val response = CacheManager.getListDataMapWithValues(requireContext(), classOfT!!)
                            viewModel.list.value = apiViewModel().responseToListItemList(response)
                        }

                    }
                    else{
                        apiViewModel().call(apiToCall!!)
                    }
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