package jp.co.apps.workout.workmanagement

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import jp.co.apps.workout.workmanagement.model.AttendanceModel
import jp.co.apps.workout.workmanagement.model.LeavingModel
import jp.co.apps.workout.workmanagement.model.WorkingTimeModel

class CostomAdapter(var context: Context, var items: ArrayList<WorkingTimeModel>): BaseAdapter() {

    private val inflater: LayoutInflater

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var v = convertView
        var holder: CustomViewHolder? = null

        v?.let {
            holder = it.tag as CustomViewHolder?
        } ?: run {
            v = inflater.inflate(R.layout.list_cell, null)
            holder = CustomViewHolder(v?.findViewById(R.id.dateText) as TextView, v?.findViewById(R.id.timeText) as TextView)
            v?.tag = holder
        }

        holder?.let {
            it.dateText.text = items[position].date
            it.timeText.text = getTimeString(items[position])
        }

        return v as View
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

    fun getTimeString(item: WorkingTimeModel): String {
        var result = ""
        item.attendance?.let {
            result = it
        }
        item.leaving?.let {
            result = "$result - $it"
        }
        return result
    }

    fun reset(newItems: ArrayList<WorkingTimeModel>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        items[index].attendance = null
        items[index].leaving = null
        MyRealm.removeData(items[index].date)
        notifyDataSetChanged()
    }

    fun editAttendance(index: Int, newDate: String) {
        items[index].attendance = newDate
        MyRealm.insert(AttendanceModel(items[index].date, newDate))
        notifyDataSetChanged()
    }

    fun editLeaving(index: Int, newDate: String) {
        items[index].leaving = newDate
        MyRealm.insert(LeavingModel(items[index].date, newDate))
        notifyDataSetChanged()
    }

    class CustomViewHolder(var dateText: TextView, var timeText: TextView)
}