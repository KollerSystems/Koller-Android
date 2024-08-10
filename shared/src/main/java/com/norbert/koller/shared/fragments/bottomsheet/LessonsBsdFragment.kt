package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.databinding.FragmentBsdTitleBinding
import com.norbert.koller.shared.recycleradapters.LessonRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.norbert.koller.shared.viewmodels.ResponseViewModel

class LessonsBsdFragment(private var programDatas : ArrayList<ProgramData>? = null) : RecyclerBsdFragment() {

    lateinit var viewModel: ResponseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]
        if(programDatas != null){
            viewModel.response.value = programDatas
        }

        getRecyclerView().adapter = LessonRecyclerAdapter(viewModel.response.value as ArrayList<ProgramData>)
        getRecyclerView().layoutManager = LinearLayoutManager(context)
        getRecyclerView().setHasFixedSize(true)

        setTitle(requireContext().getString(R.string.time_of_occupations))
    }

    companion object {
        const val TAG = "LessonsBsdFragment"
    }

}