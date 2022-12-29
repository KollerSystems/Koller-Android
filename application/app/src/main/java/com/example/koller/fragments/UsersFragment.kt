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
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_users, container, false)

        usersRecyclerView = view.findViewById(R.id.on_duty_recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        usersRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData("Norbert", "254", "🐔"),
            TodayData("Norbert", "255", "🥶"),
            TodayData("Norbert", "256", "🤓"),
            TodayData("Norbert", "257", "🦭"),
            TodayData("Norbert", "258", "🥵"),
            TodayData("Norbert", "259", "😛"),
            TodayData("Norbert", "260", "😶‍🌫️"),
            TodayData("Norbert", "261", "😵"),
            TodayData("Norbert", "262", "👼"),
            TodayData("Norbert", "263", "🐷"),
            TodayData("Norbert", "264", "❤️"),
            TodayData("Norbert", "265", "💀"),
            TodayData("Norbert", "266", "👺"),
            TodayData("Norbert", "267", "🤡"),
            TodayData("Norbert", "268", "🦄"),
            TodayData("Norbert", "269", "♋"))

        usersRecyclerView.adapter = TodayRecyclerAdapter(todayDataArrayList)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}