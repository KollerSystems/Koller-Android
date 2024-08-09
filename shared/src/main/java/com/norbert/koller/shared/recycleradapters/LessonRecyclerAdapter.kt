package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.util.rangeTo
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.DefaultDayTimes
import com.norbert.koller.shared.data.ProgramData
import com.norbert.koller.shared.databinding.ItemLessonBinding
import com.norbert.koller.shared.databinding.ItemLessonBreakBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.helpers.RecyclerViewHelper

class LessonRecyclerAdapter(private var programDatas : ArrayList<ProgramData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_LESSON = 0
        const val VIEW_TYPE_BREAK = 1
    }

    override fun getItemViewType(position: Int): Int {

        val isEven = (position % 2 == 0)
        return if(isEven){
            VIEW_TYPE_LESSON
        }else{
            VIEW_TYPE_BREAK
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LESSON -> {
                val binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LessonViewHolder(binding)
            }

            else -> {
                val binding = ItemLessonBreakBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                LessonBreakViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
        (holder.itemView.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,margin,0,margin)

        var lessonPosition = position / 2
        var lessonIndex = -1
        for (i in 0 until programDatas.size){
            val programData = programDatas[i]
            if(programData.lesson == lessonPosition){
                lessonIndex = i
                break
            }
            if(programData.lesson <= lessonPosition) {
                lessonPosition += (programData.length - 1)
            }
        }
        when (getItemViewType(position)) {
            VIEW_TYPE_LESSON -> {
                with(holder as LessonViewHolder){
                    with(itemBinding){



                        if(lessonIndex == -1){
                            textTitle.text = textTo.context.getString(R.string.doing_things)
                            textIndex.text = (lessonPosition +1).toString()

                            textFrom.text = DateTimeHelper.minuteToHourMinuteFormat(DefaultDayTimes.instance.lessons[lessonPosition].from)
                            textTo.text = DateTimeHelper.minuteToHourMinuteFormat(DefaultDayTimes.instance.lessons[lessonPosition].to)

                            dbTeacher.visibility = GONE
                            dbClassroom.getTextDescription().text = "217"
                            dbClass.visibility = GONE
                            dbState.visibility = GONE
                        }
                        else{
                            val item = programDatas[lessonIndex]
                            textTitle.text = item.topic
                            var index = (item.lesson + 1).toString()
                            var endOfLesson = item.lesson + item.length - 1
                            if(item.lesson != endOfLesson){
                                index += "\n${endOfLesson + 1}"
                            }
                            textIndex.text = index

                            textFrom.text = DateTimeHelper.minuteToHourMinuteFormat(DefaultDayTimes.instance.lessons[item.lesson].from)
                            textTo.text = DateTimeHelper.minuteToHourMinuteFormat(DefaultDayTimes.instance.lessons[endOfLesson].to)

                            dbTeacher.getTextDescription().text = item.teacher!!.name
                            dbClassroom.getTextDescription().text = item.rid.toString()
                            dbClass.visibility = GONE
                            dbState.visibility = GONE
                        }

                    }
                }


            }
            else -> {
                with(holder as LessonBreakViewHolder){
                    with(itemBinding){
                        val endOfPreviousLesson = DefaultDayTimes.instance.lessons[lessonPosition].to
                        val startOfNextLesson = DefaultDayTimes.instance.lessons[lessonPosition +1].from
                        val minutes = startOfNextLesson - endOfPreviousLesson
                        text.text = "${minutes} ${text.context.getString(R.string.minutes)}"
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        val lessonADay = DefaultDayTimes.instance.lessons.size
        var itemsToDisplay = lessonADay
        for (programBaseData in programDatas){
            itemsToDisplay -= (programBaseData.length -1)
        }
        //intermediate breaks
        itemsToDisplay += itemsToDisplay -1
        return itemsToDisplay
    }

    class LessonViewHolder(val itemBinding: ItemLessonBinding) : RecyclerView.ViewHolder(itemBinding.root)

    class LessonBreakViewHolder(val itemBinding: ItemLessonBreakBinding) : RecyclerView.ViewHolder(itemBinding.root)
}