package com.example.koller.fragments.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.*
import com.example.koller.activities.MainActivity
import com.example.koller.data.CommentData
import com.example.koller.recycleradapter.CommentRecyclerAdapter
import com.example.koller.recycleradapter.ImageRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class NewFragment : BottomSheetDialogFragment() {

    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentDataArrayList: ArrayList<CommentData>
    private lateinit var commentTil : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)
    }

    override fun onCancel(dialog: DialogInterface) {

        var addToClipboard : Boolean = true
        if(MyApplication.longEnoughToAsk(commentTil)){

            val activity = requireActivity()
            val snackbar = Snackbar
                .make((activity as MainActivity?)!!.findViewById(R.id.layout_cordinator), getString(R.string.discarded_comment_to_clipboard), 5000)
                .setAction(R.string.remove) {
                    addToClipboard = false
                }
                .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onShown(transientBottomBar: Snackbar?) {
                        super.onShown(transientBottomBar)
                    }

                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        if(addToClipboard){
                            MyApplication.setClipboard(activity, commentTil.editText!!.text.toString())
                        }
                    }
                })

            snackbar.show()




        }

        super.onCancel(dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_new, container, false)
        MyApplication.setupBottomSheet(dialog!!)
        commentTil = view.findViewById(R.id.news_til_comment)
        val buttonPost : Button = view.findViewById(R.id.news_button_post_comment)

        commentTil.editText!!.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                buttonPost.isEnabled = commentTil.editText!!.text.isNotBlank()


            }
        })

        val imageRecyclerView : RecyclerView = view.findViewById(R.id.new_recyclerview_images)
        imageRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        imageRecyclerView.setHasFixedSize(true)

        val drawableArrayList = arrayListOf(
            AppCompatResources.getDrawable(requireContext(), R.drawable.hills)!!,
            AppCompatResources.getDrawable(requireContext(), R.drawable.sh_room)!!,
            AppCompatResources.getDrawable(requireContext(), R.drawable.group_picture)!!
            )

        imageRecyclerView.adapter = ImageRecyclerAdapter(drawableArrayList, requireContext())

        commentRecyclerView = view.findViewById(R.id.recycle_view_comment)
        commentRecyclerView.layoutManager = LinearLayoutManager(context)
        commentRecyclerView.setHasFixedSize(true)

        commentDataArrayList = arrayListOf(
            CommentData("Kovács Norbert", context?.getDrawable(R.drawable.norbert), "Ez nagyon jó 🤣🤣🤣🤣"),
            CommentData("Kovács Péter", context?.getDrawable(R.drawable.pfp1), "anyad 👍"),
            CommentData("Kovács Géza", context?.getDrawable(R.drawable.pfp3), "A nagyobb társadalmi és intellektuális kontextus széles spektrumában számos szempont és érvelés áll rendelkezésre annak megítélésére, hogy egy adott állítás, tézis vagy érv alátámasztottsága milyen mértékben felel meg a valóságos körülményeknek és megfigyelhető jelenségeknek. Azonban az említett kontextus és szempontok diverzitása miatt nem mindig van lehetőségünk teljes bizonyossággal állást foglalni egy adott kérdésben, és gyakran előfordul, hogy az argumentáció valójában semmitmondó és jelentéktelen.")
        )

        commentRecyclerView.adapter = CommentRecyclerAdapter(commentDataArrayList, requireContext())

        return view
    }

    companion object {
        const val TAG = "NewFragment"
    }

}