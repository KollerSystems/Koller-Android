package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.api.ApiDataObject
import com.norbert.koller.shared.api.ApiDataObjectUser
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.DataStoreManager
import com.norbert.koller.shared.recycleradapters.ListRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListToggleApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListToggleBsdfFragmentViewModel
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response

class ListToggleApiBsdfFragment() : ListToggleBsdfFragment() {

    lateinit var pagingViewModel : ListViewModel

    //TODO: Úgy szar, ahogy van

    fun setup(activity : AppCompatActivity, apiDataObject: ApiDataObject, alreadyChecked : MutableSet<Int>? = null, title: String? = null, collapseText: Boolean = false) : ListBsdfFragment{
        setup(activity, title, collapseText)
        pagingViewModel = ViewModelProvider(activity)[ListViewModel::class.java]
        pagingViewModel.selectedItems = alreadyChecked?: mutableSetOf()
        pagingViewModel.apiDataObject = apiDataObject
        return this
    }

    fun getAdapter(): ListToggleApiRecyclerAdapter {
        return getRecyclerView().adapter as ListToggleApiRecyclerAdapter
    }

    override fun setViewModel(activity : AppCompatActivity): ListBsdfFragmentViewModel {
        return ViewModelProvider(activity)[ListToggleBsdfFragmentViewModel::class.java]
    }

    fun apiViewModel() : ListToggleBsdfFragmentViewModel {
        return viewModel as ListToggleBsdfFragmentViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pagingViewModel = ViewModelProvider(requireActivity() as AppCompatActivity)[ListViewModel::class.java]

        setRecyclerView(ListToggleApiRecyclerAdapter(this))

        pagingViewModel.onRefreshError = {

        }

        pagingViewModel.onRefreshSuccess = {

        }

        pagingViewModel.onAppendLoading = {
            Handler(Looper.getMainLooper()).post {
                getAdapter().notifyItemChanged(getAdapter().itemCount -1)
                getAdapter().notifyItemInserted(getAdapter().itemCount)
            }
        }

        pagingViewModel.onAppendError = {
            Handler(Looper.getMainLooper()).post {
                getAdapter().notifyItemChanged(getAdapter().itemCount -1)
                getRecyclerView().requestLayout()
            }
        }

        pagingViewModel.onAppendSuccess = {
            getAdapter().notifyItemRemoved(getAdapter().itemCount)
            getRecyclerView().requestLayout()
            if(getRoot().getChildAt(1) !is FrameLayout){
                getRecyclerView().post{
                    if(getAdapter().itemCount > 15){
                        createSearchView()
                    }
                }
            }


        }
        pagingViewModel.onChipsChanged = {
            getAdapter().fullRefresh()
        }


        pagingViewModel.pagingSource = {
            PagingSource(requireContext(), pagingViewModel)
        }

        lifecycleScope.launch {
            pagingViewModel.pagingData.collectLatest { pagingData ->
                getAdapter().submitData(pagingData)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun setupSearch(searchView: SearchView) {
        searchView.getEditText().doOnTextChanged{_,_,_,_->
            if(searchView.getEditText().text.isNullOrBlank()){
                pagingViewModel.search = null
            }
            else{
                pagingViewModel.search = Pair("Class.Class", ApplicationManager.searchApiWithRegex(searchView.getEditText().text!!.toString()))
            }
        }

        searchView.tag = searchView.getEditText().text.toString()
        searchView.getEditText().setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (searchView.tag != searchView.getEditText().text.toString()) {
                    pagingViewModel.onChipsChanged?.invoke()
                }

                searchView.tag = searchView.getEditText().text.toString()
            }
            false
        }
    }


    override fun onCancel(dialog: DialogInterface) {

        Log.d("TEST", getValuesOnFinish.toString())

        if(getValuesOnFinish != null) {

            val displayStringList: ArrayList<String> = arrayListOf()

            for (item in pagingViewModel.selectedItems) {

                displayStringList.add(CacheManager.getDetailsDataMapValue(pagingViewModel.apiDataObject!!.getDataType().simpleName, item)!!.getTitle())
            }

            getValuesOnFinish!!.invoke(pagingViewModel.selectedItems, displayStringList)
        }

        pagingViewModel.selectedItems = mutableSetOf()
        super.onCancel(dialog)
    }
}