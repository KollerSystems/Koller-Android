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
            CommentData("Kovács Norbert", context?.getDrawable(R.drawable.norbert), "Ez nagyon jó 🤣🤣🤣🤣"),
            CommentData("Kovács Péter", context?.getDrawable(R.drawable.pfp1), "anyad 👍"),
            CommentData("Kovács Géza", context?.getDrawable(R.drawable.pfp3), "A nagyobb társadalmi és intellektuális kontextus széles spektrumában számos szempont és érvelés áll rendelkezésre annak megítélésére, hogy egy adott állítás, tézis vagy érv alátámasztottsága milyen mértékben felel meg a valóságos körülményeknek és megfigyelhető jelenségeknek. Azonban az említett kontextus és szempontok diverzitása miatt nem mindig van lehetőségünk teljes bizonyossággal állást foglalni egy adott kérdésben, és gyakran előfordul, hogy az argumentáció valójában semmitmondó és jelentéktelen."))

        commentRecyclerView.adapter = CommentRecyclerAdapter(commentDataArrayList)

        return view
    }

    companion object {
        const val TAG = "NewFragment"
    }

}