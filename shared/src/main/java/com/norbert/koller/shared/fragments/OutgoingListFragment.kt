package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectUser



import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.OutgoingRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator

open class OutgoingListFragment() : ListFragment() {

    override fun getFragmentTitleAndDescription(): Pair<String?, String?> {
        return if(getBaseViewModel().ownerUID == CacheManager.getCurrentUserData()!!.uid){
            Pair(getString(R.string.user_outgoings),"")
        } else{
            Pair(
                (CacheManager.getDetailsDataMapValue(UserData::class.simpleName!!, getBaseViewModel().ownerUID) as UserData).name,
                getString(R.string.user_outgoings)
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (arguments != null) {
            getBaseViewModel().ownerUID = requireArguments().getInt("id", -1)
        }
        if(getBaseViewModel().ownerUID == -1){
            getBaseViewModel().ownerUID = CacheManager.getCurrentUserData()!!.uid
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectUser()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun getRecyclerAdapter(): ApiRecyclerAdapter {
        return OutgoingRecyclerAdapter()
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.newest, R.string.oldest,"Time")
        addSortingChip("Length", R.string.length, arrayListOf())
        addDateChip("Time")
        addSearchbar("Reason")
    }

}