package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity


class FragmentHolderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_holder, container, false)
    }

    lateinit var innerFragment : FragmentContainerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        innerFragment = view.findViewById(R.id.inner_fragment_container_view)
        if(childFragmentManager.fragments.size == 0) {
            toDefaultFragment()
        }
    }

    fun toDefaultFragment(){
        val mainActivity = (context as MainActivity)
        childFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        childFragmentManager.beginTransaction()
            .replace(R.id.inner_fragment_container_view, startFragment)
            .commit()
        mainActivity.showBackButton(false)
        mainActivity.changeToolbarTitleToCurrentFragmentName(startFragment)
        mainActivity.appBar.setExpanded(false)
    }

    lateinit var startFragment : Fragment



}