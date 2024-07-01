package com.norbert.koller.shared.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.WelcomeActivity

class WelcomeFinalDoneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome_final_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val buttonExit : Button = view.findViewById(R.id.button_exit)

        buttonExit.setOnClickListener{
            (requireContext() as WelcomeActivity).finishAffinity()
        }
    }
}