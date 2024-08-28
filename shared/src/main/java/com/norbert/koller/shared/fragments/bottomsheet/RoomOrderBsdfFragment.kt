package com.norbert.koller.shared.fragments.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomOrderData
import com.norbert.koller.shared.databinding.ContentFragmentBsdfRoomOrderBinding
import com.norbert.koller.shared.managers.formatDate
import kotlin.math.roundToInt

class RoomOrderBsdfFragment : BsdfFragment()  {


    lateinit var contentBinding : ContentFragmentBsdfRoomOrderBinding

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle(getString(R.string.room_order))

        val finalGradeFloat : Float = RoomOrderData.instance[0].finalGrade.toFloat() / 2
        val finalGradeString = if(finalGradeFloat != (finalGradeFloat).roundToInt().toFloat()){
            "${(finalGradeFloat + 0.5f).roundToInt()},"
        }
        else{
            finalGradeFloat.toString()
        }
        addEndText(finalGradeString)

        contentBinding.footer.textName.text = RoomOrderData.instance[0].teacher.name


        contentBinding.footer.textDate.text = RoomOrderData.instance[0].date.formatDate("MM. dd.")

        contentBinding.recyclerView.layoutManager = LinearLayoutManager(context)
        contentBinding.recyclerView.setHasFixedSize(true)
    }

    override fun getContentHolder(inflater: LayoutInflater): ViewGroup {
        contentBinding = ContentFragmentBsdfRoomOrderBinding.inflate(layoutInflater)
        return contentBinding.root
    }

    companion object {
        const val TAG = "RoomOrderBottomSheet"
    }
}