package com.norbert.koller.shared.fragments.bottomsheet.list

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ListToggleApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentPropertiesViewModel
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentToggleViewModel
import com.norbert.koller.shared.viewmodels.ListViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListToggleApiBsdfFragment() : ListBsdfFragment() {

    lateinit var pagingViewModel : ListViewModel

    fun getAdapter(): ListToggleApiRecyclerAdapter {
        return getRecyclerView().adapter as ListToggleApiRecyclerAdapter
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        pagingViewModel = ViewModelProvider(this)[ListViewModel::class.java]

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



            val displayStringList: ArrayList<String> = arrayListOf()

            for (item in pagingViewModel.selectedItems) {

                displayStringList.add(CacheManager.getDetailsDataMapValue(pagingViewModel.apiDataObject!!.getDataType().simpleName, item)!!.getTitle())
            }

        /*setFragmentResult("", Bundle(pagingViewModel.selectedItems, displayStringList))*/



        pagingViewModel.selectedItems = mutableSetOf()
        super.onCancel(dialog)
    }
}