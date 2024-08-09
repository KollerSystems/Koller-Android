package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.norbert.koller.shared.activities.MainActivity

abstract class FragmentInMainActivity : Fragment() {

    open fun getFragmentTitleAndDescription() : Pair<String?, String?>?{
        return null
    }
    open fun getFragmentTitle() : String?{
        return null
    }

    fun getMainActivity() : MainActivity{
        return (requireContext() as MainActivity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pair = getFragmentTitleAndDescription()
        if(pair != null) {
            getMainActivity().setToolbarTitle(pair.first, pair.second?:"")
        }
        else{
            val title = getFragmentTitle()
            if(title != null) {
                getMainActivity().setToolbarTitle(title)
            }
        }
    }

}