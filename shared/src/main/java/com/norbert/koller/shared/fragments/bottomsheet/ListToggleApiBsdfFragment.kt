package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleApiRecyclerAdapter
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListStaticToggleBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListToggleApiBsdfFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class ListToggleApiBsdfFragment() : ListToggleBsdfFragment() {

    fun setup(activity : AppCompatActivity, alreadyChecked : ArrayList<String>? = null, title: String? = null, collapseText: Boolean = false) : ListBsdfFragment{
        setup(activity, title, collapseText)
        return this
    }

    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListToggleApiBsdfFragmentViewModel::class.java]
    }

    fun apiViewModel() : ListToggleApiBsdfFragmentViewModel{
        return viewModel as ListToggleApiBsdfFragmentViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            /* apiViewModel().classOfT = classOfT!!

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
            */
        }

        super.onViewCreated(view, savedInstanceState)

        viewModel.list.observe(this){
            if(it == null){
                dismiss()
                var snackbar = (requireContext() as MainActivity).getSnackBar(getString(R.string.an_error_occurred), Snackbar.LENGTH_SHORT)
                snackbar.show()
            }
            else{
                setRecyclerView(ListToggleApiRecyclerAdapter(this))
            }
        }
    }

}