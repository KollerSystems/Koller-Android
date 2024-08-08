package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.activities.MainActivity

abstract class FragmentInMainActivity : Fragment() {

    abstract fun getFragmentTitle() : String?

    fun getMainActivity() : MainActivity{
        return (requireContext() as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(getFragmentTitle() != null) {
            getMainActivity().setToolbarTitle(getFragmentTitle())
        }
    }

}