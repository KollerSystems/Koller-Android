package com.norbert.koller.shared.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.CreateNewPostActivity
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.EventsData
import com.norbert.koller.shared.data.TodayData
import com.norbert.koller.shared.fragments.bottomsheet.BottomFragmentPostTypes
import com.norbert.koller.shared.recycleradapter.EventsRecyclerAdapter
import com.norbert.koller.shared.recycleradapter.TodayRecyclerAdapter
import java.util.ArrayList
import java.util.Date

abstract class HomeFragment : Fragment() {

    
    
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventsDataArrayList: ArrayList<EventsData>

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>
    private lateinit var unreadRecyclerView: RecyclerView
    private lateinit var unreadDataArrayList: ArrayList<TodayData>
    lateinit var refresh : SwipeRefreshLayout





    var time : Float = 0.0f
    

    lateinit var outgoingTimer : CountDownTimer
    var outgoingTimerRunning = false
    lateinit var lessonTimer : CountDownTimer
    var lessonsTimerRunning = false

    abstract fun responsibleLayout() : Int

    override fun onResume() {
        super.onResume()
        val mainActivity = context as MainActivity
        mainActivity.setToolbarTitle(mainActivity.getString(R.string.home), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)

        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.setHasFixedSize(true)

        eventsDataArrayList = arrayListOf(
            EventsData("Túra a hegyen", "Ismét túrát szervezünk, azonban most a Shogelbogen hegyen. Nagyon szép az egész eg minden, nagyon jó lesz eskü. Gyertek sokan. Szép a táj meg minden. Eskü jó. Minden pacek lesz. Tuti fix.", "test", "A hegy", Date(85965436), 2),
            EventsData("UI design verseny", "A Ralix megkereste a kollégiumunkot, hogy van-e igény egy user interface tervező versenyre. Állítólag egy kifejezetten nagy nyereménye lenne a versenynek. Kérek mindenkit aki érdeklődik ez iránt reagáljon egy like-al erre a bejegyzésre.", "test"),
            EventsData("Kovács Gábor az év kollégistája", "Az idei legjobb kollégista díjat Kovács Gábor, 11-es Pusksásos diák nyerte. Kovács Gábor egy olyan ember a Kollégisták szerint, aki ahol baj van ott segít. Ajtókat javít, gyorskötőzőzik és még fűrészel is ha kell.", "test"),
            EventsData("Jön a nyári szünet", "Az idei tanévnek is vége van, ugyan hosszú volt, de ez mindig így van. Most azonban elkezdődik a 2 és fél hómapőos nyári szünet, amire mindenki annyira várt. Mik voltak a kedvenc pillanataid a kollégiumban idén? Írd le a kommentek közé", "test"),
            EventsData("Andrásosfi Norberto", "Kicsoda Andrásosfi? Egy távoli hang kérdezi. Andrásosfi egy Linux user, aki szereti hangosan szidni a Windowst. Sértően beszél a diákokkal, de legalább jól érzi magát a LoL meccsek között, mivel", "test")
        )

        eventsRecyclerView.adapter = EventsRecyclerAdapter(eventsDataArrayList)

        refresh = view.findViewById(R.id.home_refresh)



        val buttonShowAllPosts : Button = view.findViewById(R.id.home_button_show_all_posts)
        buttonShowAllPosts.setOnClickListener{
            //(context as MainActivity).changeFragment(PostsFragment())
        }

        todayRecyclerView = view.findViewById(R.id.todayRecyclerView)
        todayRecyclerView.layoutManager = LinearLayoutManager(context)
        todayRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.room), getString(R.string.room_order), "K, P", "4,"),
            TodayData(false, context?.getDrawable(R.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mert miért ne."
            )
        )

        val adapter = TodayRecyclerAdapter(todayDataArrayList, requireContext())
        todayRecyclerView.adapter = adapter





        unreadRecyclerView = view.findViewById(R.id.home_recycle_view_unread)
        unreadRecyclerView.layoutManager = LinearLayoutManager(context)
        unreadRecyclerView.setHasFixedSize(true)

        unreadDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(R.drawable.room),getString(R.string.room_order), getString(
                R.string.perfect), "5")
        )

        unreadRecyclerView.adapter = TodayRecyclerAdapter(unreadDataArrayList, requireContext())

        val fabRoot: View = view.findViewById(R.id.home_fab_root)

        fabRoot.setOnClickListener{
            val dialog = BottomFragmentPostTypes()

            dialog.show(requireActivity().supportFragmentManager, BottomFragmentPostTypes.TAG)

            fun openCreateNewPostActivity(type : String){
                val intent = Intent(view.context, CreateNewPostActivity::class.java)
                intent.putExtra("type", type)
                startActivity(intent)
                dialog.dismiss()
            }

            fabRoot.post({
                dialog.view?.findViewById<View>(R.id.post_type_ly_post)?.setOnClickListener{
                    openCreateNewPostActivity(getString(R.string.general_post))
                }
                dialog.view?.findViewById<View>(R.id.post_type_ly_program)?.setOnClickListener{
                    openCreateNewPostActivity(getString(R.string.program))
                }
                dialog.view?.findViewById<View>(R.id.post_type_ly_news)?.setOnClickListener{
                    openCreateNewPostActivity(getString(R.string.news_one))
                }
            })




        }
    }
}