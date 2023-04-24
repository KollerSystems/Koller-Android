package com.example.koller

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomFragmentPostTypes : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.bottom_fragment_post_types, container, false)

        fun openCreateNewPostActivity(type : String){
            val intent = Intent(view.context, CreateNewPostActivity::class.java)
            intent.putExtra("type", type)
            startActivity(intent)
            dismiss()
        }

        view.findViewById<View>(R.id.post_type_ly_post).setOnClickListener{
            openCreateNewPostActivity(getString(R.string.general_post))
        }
        view.findViewById<View>(R.id.post_type_ly_program).setOnClickListener{
            openCreateNewPostActivity(getString(R.string.program))
        }
        view.findViewById<View>(R.id.post_type_ly_news).setOnClickListener{
            openCreateNewPostActivity(getString(R.string.news_one))
        }

        return view
    }



    companion object {
        const val TAG = "PostTypesBottomSheet"
    }
}
