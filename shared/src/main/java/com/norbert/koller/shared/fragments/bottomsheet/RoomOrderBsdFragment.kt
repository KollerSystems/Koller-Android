package com.norbert.koller.shared.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomOrderData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.databinding.FragmentBsdRoomOrderBinding
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.managers.setupBottomSheet
import kotlin.math.roundToInt

class RoomOrderBsdFragment : BottomSheetDialogFragment()  {


    lateinit var binding : FragmentBsdRoomOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBsdRoomOrderBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog!!.setupBottomSheet()

        val finalGradeFloat : Float = RoomOrderData.instance[0].finalGrade.toFloat() / 2
        if(finalGradeFloat != (finalGradeFloat).roundToInt().toFloat()){
            binding.textGrade.text = "${(finalGradeFloat + 0.5f).roundToInt()},"
        }
        else{
            binding.textGrade.text = finalGradeFloat.toString()
        }

        binding.footer.textName.text = RoomOrderData.instance[0].teacher.name


        binding.footer.textDate.text = RoomOrderData.instance[0].date.formatDate("MM. dd.")

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
    }

    companion object {
        const val TAG = "RoomOrderBottomSheet"
    }
}