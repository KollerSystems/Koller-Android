package com.example.koller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewFragment : BottomSheetDialogFragment() {

    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentDataArrayList: ArrayList<CommentData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_new, container, false)

        commentRecyclerView = view.findViewById(R.id.recycle_view_comment)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.setHasFixedSize(true)

        commentDataArrayList = arrayListOf(
            CommentData("Kis Norbert", context?.getDrawable(R.drawable.norbert), "Ez kurva jó 🤣🤣🤣🤣"),
            CommentData("Kovács Péter", context?.getDrawable(R.drawable.pfp1), "anyad"),
            CommentData("Smoll Cock", context?.getDrawable(R.drawable.pfp3), "Hallód? Mi lenne, ha végre eljönnél te is Mondoconra? Nagyon unalmas már, hogy mindig banánként táncikolsz a híd alatt."))

        commentRecyclerView.adapter = CommentRecyclerAdapter(commentDataArrayList)

        return view
    }

    companion object {
        const val TAG = "NewFragment"
    }

}