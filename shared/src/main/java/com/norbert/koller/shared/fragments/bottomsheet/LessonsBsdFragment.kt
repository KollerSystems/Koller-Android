package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.databinding.FragmentBsdListBinding
import com.norbert.koller.shared.recycleradapters.LessonRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ListAdapter
import com.norbert.koller.shared.viewmodels.MainActivityViewModel
import com.norbert.koller.shared.viewmodels.ResponseViewModel

class LessonsBsdFragment(private var programDatas : ArrayList<ProgramData>? = null) : BottomSheetDialogFragment() {

    lateinit var binding : FragmentBsdListBinding
    lateinit var viewModel: ResponseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBsdListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ResponseViewModel::class.java]
        if(programDatas != null){
            viewModel.response.value = programDatas
        }

        binding.recyclerView.adapter = LessonRecyclerAdapter(viewModel.response.value as ArrayList<ProgramData>)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
    }

    companion object {
        const val TAG = "LessonsBsdFragment"
    }

}