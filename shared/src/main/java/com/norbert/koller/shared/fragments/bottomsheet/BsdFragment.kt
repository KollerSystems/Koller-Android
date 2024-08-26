package com.norbert.koller.shared.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.databinding.FragmentBsdTitleBinding
import com.norbert.koller.shared.managers.getAttributeColor

abstract class BsdFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBsdTitleBinding
    lateinit var viewGroup : ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBsdTitleBinding.inflate(layoutInflater)
        viewGroup = getContentHolder(inflater)
        viewGroup.setBackgroundColor(requireContext().getAttributeColor(com.google.android.material.R.attr.colorSurface))
        binding.root.addView(viewGroup)
        if(context is MainActivity) {
            if ((context as MainActivity).viewModel.currentBottomSheetDialogFragment != null) {
                (context as MainActivity).viewModel.currentBottomSheetDialogFragment!!.dismiss()
            }
            (context as MainActivity).viewModel.currentBottomSheetDialogFragment = this
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if(context is MainActivity) {
            if ((context as MainActivity).viewModel.currentBottomSheetDialogFragment == this) {
                (context as MainActivity).viewModel.currentBottomSheetDialogFragment = null
            }
        }
    }

    abstract fun getContentHolder(inflater: LayoutInflater) : ViewGroup

    fun setTitle(title : String){
        binding.textTitle.visibility = VISIBLE
        binding.textTitle.text = title
    }

    fun getRoot() : ViewGroup{
        return binding.root
    }

}