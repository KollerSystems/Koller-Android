package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.recycleradapters.LessonRecyclerAdapter
import com.norbert.koller.shared.viewmodels.DetailsViewModel

class LessonsBsdfFragment(private var programDatas : ArrayList<ProgramData>? = null) : RecyclerBsdfFragment() {

    lateinit var viewModel: DetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
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