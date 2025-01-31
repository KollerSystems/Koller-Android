package com.norbert.koller.student.fragments.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import com.norbert.koller.student.databinding.FragmentDAcquiredKeyBinding


class AcquiredKeyDFragment : DialogFragment() {

    private lateinit var binding : FragmentDAcquiredKeyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDAcquiredKeyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.stars.onStart()
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun dismiss() {
        super.dismiss()
        requireActivity().finish()
    }

    companion object {
        const val TAG = "AcquiredKeyDFragment"
    }
}