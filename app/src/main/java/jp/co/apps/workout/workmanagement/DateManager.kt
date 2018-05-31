package jp.co.apps.workout.workmanagement

import jp.co.apps.workout.workmanagement.model.WorkingTimeModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DateManager(private val mCalender: Calendar, val days: ArrayList<WorkingTimeModel> = ArrayList()) {

    init {
        setDays()
    }

    private fun setDays() {
        days.removeAll{ it.javaClass == WorkingTimeModel::class.java }
        val cal = Calendar.getInstance()
        cal.set(mCalender.get(Calendar.YEAR), mCalender.get(Calendar.MONTH)-1, 1)
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE))
        do {
            cal.add(Calendar.DATE, 1)
            val dateStr = SimpleDateFormat("yyyy/MM/dd", Locale.US).format(cal.time)
            days.add(WorkingTimeModel(dateStr))
        } while (cal.get(Calendar.MONTH) == mCalender.get(Calendar.MONTH))
    }

    fun nextMonth() {
        mCalender.add(Calendar.MONTH, 1)
        setDays()
    }

    fun prevMonth() {
        mCalender.add(Calendar.MONTH, -1)
        setDays()
    }

    fun getMonth(): String {
        return SimpleDateFormat("yyyy/MM", Locale.US).format(mCalender.time)
    }
}