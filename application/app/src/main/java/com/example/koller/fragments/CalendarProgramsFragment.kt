package com.example.koller.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.data.EventsData
import com.example.koller.recycleradapter.EventsRecyclerAdapter
import com.example.koller.R

class CalendarProgramsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventsDataArrayList: ArrayList<EventsData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_calendar_programs, container, false)

        eventsRecyclerView = view.findViewById(R.id.recycler_view)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.setHasFixedSize(true)

        eventsDataArrayList = arrayListOf(
            EventsData("Titkos Burger", "A kód: Titkós\nLorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Hitler mégesm halt meg!", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Az összes magyar iskola felrobbant!", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Joe", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test"),
            EventsData("Juhos Gergely xdddd", "Lorem ipsum dolor sit blbalball dofgdask fsjkl  sdfjkéla arpofs iojofsdl sfdjopfds", "test")
        )

        eventsRecyclerView.adapter = EventsRecyclerAdapter(eventsDataArrayList)

        return view
    }
}