package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.R
import com.example.koller.TodayData
import com.example.koller.TodayRecyclerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotificationsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var notificsRecyclerView: RecyclerView
    private lateinit var notificationDataArrayList: ArrayList<TodayData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificsRecyclerView = view.findViewById(R.id.recycleview_notifications)
        notificsRecyclerView.layoutManager = LinearLayoutManager(context)
        notificsRecyclerView.setHasFixedSize(true)

        notificationDataArrayList = arrayListOf(
            TodayData("Szobarend", "K, P", "4"),
            TodayData("Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mivel találkoztam vele a Mondoconon", context?.getDrawable(R.drawable.award)
            )
        )

        notificsRecyclerView.adapter = TodayRecyclerAdapter(notificationDataArrayList)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotificationsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}