package com.norbert.koller.shared.fragments

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
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CommentData
import com.norbert.koller.shared.recycleradapters.CommentRecyclerAdapter
import com.norbert.koller.shared.recycleradapters.ImageRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.norbert.koller.shared.managers.setupBottomSheet


class PostFragment : BottomSheetDialogFragment() {

    private lateinit var commentRecyclerView: RecyclerView
    private lateinit var commentDataArrayList: ArrayList<CommentData>
    private lateinit var commentTil : TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogFull)
    }

    override fun onCancel(dialog: DialogInterface) {

        var addToClipboard = true
        if(ApplicationManager.longEnoughToAsk(commentTil)){

            val activity = requireActivity()
            val snackbar = Snackbar
                .make(activity.findViewById(R.id.coordinator), getString(R.string.discarded_comment_to_clipboard), Snackbar.LENGTH_LONG)
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
                            ApplicationManager.setClipboard(activity, commentTil.editText!!.text.toString())
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
        val view : View = inflater.inflate(R.layout.fragment_bhd_post, container, false)
        dialog!!.setupBottomSheet()
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
            CommentData("Kovács Péter", context?.getDrawable(R.drawable.pfp_juhos), "anyad 👍"),
            CommentData("Kovács Géza", context?.getDrawable(R.drawable.pfp_robert), "A nagyobb társadalmi és intellektuális kontextus széles spektrumában számos szempont és érvelés áll rendelkezésre annak megítélésére, hogy egy adott állítás, tézis vagy érv alátámasztottsága milyen mértékben felel meg a valóságos körülményeknek és megfigyelhető jelenségeknek. Azonban az említett kontextus és szempontok diverzitása miatt nem mindig van lehetőségünk teljes bizonyossággal állást foglalni egy adott kérdésben, és gyakran előfordul, hogy az argumentáció valójában semmitmondó és jelentéktelen.")
        )

        commentRecyclerView.adapter = CommentRecyclerAdapter(commentDataArrayList, requireContext())



        /*chipAddToCalendar.setOnClickListener{

            val cr: ContentResolver = requireContext().contentResolver

            if(chipAddToCalendar.isChecked){

                val values = ContentValues()

                values.put(CalendarContract.Events.DTSTART, "1687471200000")
                values.put(CalendarContract.Events.DTEND, "1687471290000")
                values.put(CalendarContract.Events.TITLE, "Title")
                values.put(CalendarContract.Events.DESCRIPTION, getString(R.string.automatically_generated_event_text))
                values.put(CalendarContract.Events.EVENT_LOCATION, "Valahol")

                val timeZone: TimeZone = TimeZone.getDefault()
                values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.id)

                values.put(CalendarContract.Events.CALENDAR_ID, 1)


                values.put(CalendarContract.Events._ID, 1239999999)
                values.put(CalendarContract.Events.CUSTOM_APP_PACKAGE, "com.norbert.koller.koller")

                cr.insert(CalendarContract.Events.CONTENT_URI, values)
            }
            else{
                val eventUri: Uri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, 1239999999)
                cr.delete(eventUri, null, null)
            }
        }*/
        return view
    }

    companion object {
        const val TAG = "NewFragment"
    }

}