package com.example.koller.fragments

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.koller.R
import com.example.shared.R as Rs
import com.example.shared.activities.CreateNewPostActivity
import com.example.koller.activities.MainActivity
import com.example.koller.activities.SettingsActivity
import com.example.shared.MyApplication
import com.example.shared.data.DefaultDayTimes
import com.example.shared.data.EventsData
import com.example.shared.data.TodayData
import com.example.shared.fragments.bottomsheet.BottomFragmentPostTypes
import com.example.shared.navigateWithDefaultAnimation
import com.example.shared.recycleradapter.EventsRecyclerAdapter
import com.example.shared.recycleradapter.TodayRecyclerAdapter
import java.util.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var eventsDataArrayList: ArrayList<EventsData>

    private lateinit var todayRecyclerView: RecyclerView
    private lateinit var todayDataArrayList: ArrayList<TodayData>
    private lateinit var unreadRecyclerView: RecyclerView
    private lateinit var unreadDataArrayList: ArrayList<TodayData>

    private lateinit var viewStayOutSlider: View
    private lateinit var viewLessonSlider: View

    private lateinit var textStayOutTop: TextView
    private lateinit var textStayOutBottom: TextView

    var time : Float = 0.0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var outgoingTimer : CountDownTimer
    var outgoingTimerRunning = false
    lateinit var lessonTimer : CountDownTimer
    var lessonsTimerRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        viewStayOutSlider = view.findViewById(R.id.home_view_stay_slider)
        textStayOutTop = view.findViewById(R.id.home_text_outgoing_top)
        textStayOutBottom = view.findViewById(R.id.home_text_outgoing_down)
        viewLessonSlider = view.findViewById(R.id.home_view_lesson_slider)
        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)

        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.setHasFixedSize(true)

        eventsDataArrayList = arrayListOf(
            EventsData("Túra a hegyen", "Ismét túrát szervezünk, azonban most a Shogelbogen hegyen. Nagyon szép az egész eg minden, nagyon jó lesz eskü. Gyertek sokan. Szép a táj meg minden. Eskü jó. Minden pacek lesz. Tuti fix.", "test"),
            EventsData("UI design verseny", "A Ralix megkereste a kollégiumunkot, hogy van-e igény egy user interface tervező versenyre. Állítólag egy kifejezetten nagy nyereménye lenne a versenynek. Kérek mindenkit aki érdeklődik ez iránt reagáljon egy like-al erre a bejegyzésre.", "test"),
            EventsData("Kovács Gábor az év kollégistája", "Az idei legjobb kollégista díjat Kovács Gábor, 11-es Pusksásos diák nyerte. Kovács Gábor egy olyan ember a Kollégisták szerint, aki ahol baj van ott segít. Ajtókat javít, gyorskötőzőzik és még fűrészel is ha kell.", "test"),
            EventsData("Jön a nyári szünet", "Az idei tanévnek is vége van, ugyan hosszú volt, de ez mindig így van. Most azonban elkezdődik a 2 és fél hómapőos nyári szünet, amire mindenki annyira várt. Mik voltak a kedvenc pillanataid a kollégiumban idén? Írd le a kommentek közé", "test"),
            EventsData("Andrásosfi Norberto", "Kicsoda Andrásosfi? Egy távoli hang kérdezi. Andrásosfi egy Linux user, aki szereti hangosan szidni a Windowst. Sértően beszél a diákokkal, de legalább jól érzi magát a LoL meccsek között, mivel", "test")
        )

        eventsRecyclerView.adapter = EventsRecyclerAdapter(eventsDataArrayList)

        val refresh : SwipeRefreshLayout = view.findViewById(R.id.home_refresh)

        val cardOutgoing : View = view.findViewById(R.id.home_card_outgoing)
        cardOutgoing.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = R.id.studentHostelNest
            findNavController().navigate(R.id.action_studentHostelFragment_to_userOutgoingFragment)
        }

        val cardLessons : View = view.findViewById(R.id.home_card_lessons)
        cardLessons.setOnClickListener{
            (requireActivity() as MainActivity).bottomNavigationView.selectedItemId = R.id.calendarFragment
        }

        val buttonShowAllPosts : Button = view.findViewById(R.id.home_button_show_all_posts)
        buttonShowAllPosts.setOnClickListener{
            findNavController().navigateWithDefaultAnimation(R.id.action_homeFragment_to_postsFragment)
        }

        fun setupHome(){
            var c : Calendar = Calendar.getInstance()
            val seconds : Long = ((c.get(Calendar.SECOND) + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.HOUR_OF_DAY) * 60 * 60) + (SettingsActivity.timeOffset *60 *60)).toLong()
            val minutes : Float = seconds.toFloat() / 60

            fun startTimerForLessons(slider : View, startTimeMinute : Int, endTimeMinute : Int){
                var lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                var nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                var remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

                outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                        val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)

                        (slider.layoutParams as ConstraintLayout.LayoutParams)
                            .matchConstraintPercentWidth = solid + moving
                        slider.requestLayout()
                    }

                    override fun onFinish() {
                        lessonsTimerRunning = false
                    }
                }.start()
            }

            fun startTimerForOutgoing (slider : View, startTimeMinute : Int, endTimeMinute : Int){

                var lastLessonMilli = (startTimeMinute * 60f * 1000).toLong()
                var nightTimeGoInMilli = (endTimeMinute * 60f * 1000).toLong()
                var remainingTimeOnUpdateMilli : Long = nightTimeGoInMilli - (seconds * 1000)

                val hours: Int = endTimeMinute / 60 // since both are ints, you get an int
                var minutes: String = (endTimeMinute % 60).toString()
                if(minutes.length == 1) minutes = "0$minutes"
                val nightTimeGoInsideForText : String = "$hours:$minutes"

                var outStringId : Int = 0
                var remainStringId : Int = 0
                if(false){ // inside
                    outStringId = Rs.string.go_out_text
                    remainStringId = Rs.string.will_remain
                }
                else{
                    outStringId = Rs.string.stay_out_text
                    remainStringId = Rs.string.now_remain
                }
                textStayOutTop.text = getString(outStringId, nightTimeGoInsideForText)

                outgoingTimer = object : CountDownTimer(remainingTimeOnUpdateMilli, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val solid : Float = (remainingTimeOnUpdateMilli.toFloat() /  (nightTimeGoInMilli - lastLessonMilli) * -1) + 1
                        val moving : Float = (((millisUntilFinished.toFloat() / remainingTimeOnUpdateMilli) * -1) + 1) * (1 - solid)
                        outgoingTimerRunning = true

                        if((millisUntilFinished / 1000f / 60 / 60) < 0.25f){
                            slider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorErrorContainer))
                        }
                        else{
                            slider.setBackgroundColor(MyApplication.getAttributeColor(requireContext(), com.google.android.material.R.attr.colorPrimaryContainer))
                        }

                        var remainText : String = ""

                        var hoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60 + 1) / 60) - 0.5f).roundToInt()
                        var minutesWithoutHoursUntilFinished : Int = (((millisUntilFinished / 1000f / 60) - (hoursUntilFinished * 60) + 0.5f)).roundToInt()

                        if(hoursUntilFinished != 0){
                            var hoursString : String
                            if(minutesWithoutHoursUntilFinished != 0){
                                if(hoursUntilFinished > 1) {
                                    hoursString = getString(Rs.string.hours)
                                }
                                else{
                                    hoursString = getString(Rs.string.hour)
                                }
                            }
                            else{
                                if(hoursUntilFinished > 1) {
                                    hoursString = getString(Rs.string.hours_infelcted)
                                }
                                else{
                                    hoursString = getString(Rs.string.hour_inflected)
                                }
                            }
                            remainText += "$hoursUntilFinished $hoursString "
                        }
                        if(minutesWithoutHoursUntilFinished != 0){

                            var minutesString : String
                            if(minutesWithoutHoursUntilFinished > 1) {
                                minutesString = getString(Rs.string.minutes_infelcted)
                            }
                            else{
                                minutesString = getString(Rs.string.minute)
                            }

                            remainText += "$minutesWithoutHoursUntilFinished $minutesString "
                        }


                        textStayOutBottom.text = getString(remainStringId, remainText)

                        (slider.layoutParams as ConstraintLayout.LayoutParams)
                            .matchConstraintPercentWidth = solid + moving
                        slider.requestLayout()
                    }

                    override fun onFinish() {
                        outgoingTimerRunning = false
                    }
                }.start()
            }

            if(minutes >= DefaultDayTimes.instance.dayTimeStart &&
                minutes <= DefaultDayTimes.instance.dayTimeGoInside){

                cardOutgoing.visibility = VISIBLE

                startTimerForOutgoing(viewStayOutSlider, DefaultDayTimes.instance.dayTimeStart, DefaultDayTimes.instance.dayTimeGoInside)
            }
            else if(minutes >= DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to &&
                minutes <= DefaultDayTimes.instance.nightTimeGoInsideYellow){

                cardOutgoing.visibility = VISIBLE

                startTimerForOutgoing(viewStayOutSlider, DefaultDayTimes.instance.lessons[DefaultDayTimes.instance.lessons.size -1].to, DefaultDayTimes.instance.nightTimeGoInsideYellow)

            }
            else{
                cardOutgoing.visibility = GONE
            }

            var lessonTime : Boolean = false

            var textLessonsSilenceWarning : TextView = view.findViewById(R.id.home_text_silence_warning)

            var textLessonsTitle : TextView = view.findViewById(R.id.home_text_lessons_title)
            var textLessonsDescription : TextView = view.findViewById(R.id.home_text_lessons_description)
            var textLessonsNumber : TextView = view.findViewById(R.id.home_text_lessons_number)
            var textLessonsTime : TextView = view.findViewById(R.id.home_text_lessons_time)

            var viewLessonsNext : View = view.findViewById(R.id.home_layout_lessons_next)
            var textSmallLessonsTitle : TextView = view.findViewById(R.id.home_text_small_lessons_title)
            var textSmallLessonsDescription : TextView = view.findViewById(R.id.home_text_small_lessons_description)
            var textSmallLessonsNumber : TextView = view.findViewById(R.id.home_text_small_lessons_number)
            var textSmallLessonsTime : TextView = view.findViewById(R.id.home_text_small_lessons_time)

            fun NextLessonsGraphic(index : Int){
                viewLessonsNext.visibility = VISIBLE

                var lesson = DefaultDayTimes.instance.lessons[index + 1]
                textSmallLessonsNumber.text = (index + 1 + 1).toString() + "."
                textSmallLessonsTime.text = MyApplication.timeFromTo(lesson.from, lesson.to)
            }

            for (i in 0 until DefaultDayTimes.instance.lessons.size){
                //óra
                if(minutes >= DefaultDayTimes.instance.lessons[i].from &&
                    minutes <= DefaultDayTimes.instance.lessons[i].to){
                    lessonTime = true

                    var currentLessonTime = DefaultDayTimes.instance.lessons[i]

                    cardLessons.visibility = VISIBLE
                    textLessonsSilenceWarning.visibility = VISIBLE

                    textLessonsNumber.text = (i + 1).toString() + "."
                    textLessonsNumber.background = null

                    textLessonsTime.text = MyApplication.timeFromTo(currentLessonTime.from, currentLessonTime.to)

                    startTimerForLessons(viewLessonSlider, currentLessonTime.from, currentLessonTime.to)

                    if(i < DefaultDayTimes.instance.lessons.size - 1){
                        var nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]
                        NextLessonsGraphic(i)
                    }
                    else{
                        viewLessonsNext.visibility = GONE
                    }

                }
                //szünet
                else if (i < DefaultDayTimes.instance.lessons.size - 1 &&
                    minutes >= DefaultDayTimes.instance.lessons[i].to &&
                    minutes <= DefaultDayTimes.instance.lessons[i + 1].from){
                    lessonTime = true

                    NextLessonsGraphic(i)

                    var currentLessonTime = DefaultDayTimes.instance.lessons[i]
                    var nextLessonTime = DefaultDayTimes.instance.lessons[i + 1]

                    cardLessons.visibility = VISIBLE
                    textLessonsSilenceWarning.visibility = GONE


                    textLessonsTitle.text = getText(Rs.string.pause)
                    textLessonsDescription.text = getText(Rs.string.pause_description)
                    textLessonsNumber.text = ""
                    textLessonsNumber.background = requireContext().getDrawable(Rs.drawable.pause)
                    textLessonsTime.text = MyApplication.timeFromTo(currentLessonTime.to, nextLessonTime.from)

                    startTimerForLessons(viewLessonSlider, currentLessonTime.to, nextLessonTime.from)
                }
            }

            if(!lessonTime){
                cardLessons.visibility = GONE
            }


            val textNow : TextView = view.findViewById(R.id.home_text_now)

            if(cardOutgoing.visibility == VISIBLE || cardLessons.visibility == VISIBLE){
                textNow.visibility = VISIBLE
            }
            else{
                textNow.visibility = GONE
            }
        }

        setupHome()

        refresh.setOnRefreshListener{
            cancelIfNeeded()
            setupHome()
            refresh.isRefreshing = false
        }

        todayRecyclerView = view.findViewById(R.id.todayRecyclerView)
        todayRecyclerView.layoutManager = LinearLayoutManager(context)
        todayRecyclerView.setHasFixedSize(true)

        todayDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(Rs.drawable.room), getString(Rs.string.room_order), "K, P", "4,"),
            TodayData(false, context?.getDrawable(Rs.drawable.award),"Igazgatói dicséret", "Katona Márton Barnabást igazgatói dicséretben részesítem, mert miért ne."
            )
        )

        var adapter = TodayRecyclerAdapter(todayDataArrayList, requireContext())
        todayRecyclerView.adapter = adapter





        unreadRecyclerView = view.findViewById(R.id.home_recycle_view_unread)
        unreadRecyclerView.layoutManager = LinearLayoutManager(context)
        unreadRecyclerView.setHasFixedSize(true)

        unreadDataArrayList = arrayListOf(
            TodayData(false, context?.getDrawable(Rs.drawable.room),getString(Rs.string.room_order), getString(Rs.string.perfect), "5")
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

            fabRoot.post(Runnable {
                dialog.view?.findViewById<View>(Rs.id.post_type_ly_post)?.setOnClickListener{
                    openCreateNewPostActivity(getString(Rs.string.general_post))
                }
                dialog.view?.findViewById<View>(Rs.id.post_type_ly_program)?.setOnClickListener{
                    openCreateNewPostActivity(getString(Rs.string.program))
                }
                dialog.view?.findViewById<View>(Rs.id.post_type_ly_news)?.setOnClickListener{
                    openCreateNewPostActivity(getString(Rs.string.news_one))
                }
            })




        }

        return view
    }

    fun cancelIfNeeded(){
        if (lessonsTimerRunning)
            lessonTimer.cancel()
        if (outgoingTimerRunning)
            outgoingTimer.cancel()
    }

    override fun onDestroyView() {
        cancelIfNeeded()
        super.onDestroyView()
    }

}

