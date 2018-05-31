package jp.co.apps.workout.workmanagement.fragment

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import jp.co.apps.workout.workmanagement.CostomAdapter
import jp.co.apps.workout.workmanagement.DateManager
import jp.co.apps.workout.workmanagement.R
import jp.co.apps.workout.workmanagement.model.WorkingTimeModel
import java.text.SimpleDateFormat
import java.util.*


class ListFragment : Fragment() {

    private lateinit var mList: ListView
    lateinit var titleText: TextView
    private val dateManager = DateManager(Calendar.getInstance())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        mList = view.findViewById(R.id.workingList)
        titleText = view.findViewById(R.id.titleText)
        mList.adapter = CostomAdapter(this.requireContext(), dateManager.days)
        titleText.text = dateManager.getMonth()


        val prevButton = view.findViewById<Button>(R.id.prevButton)
        prevButton.setOnClickListener {
            dateManager.prevMonth()
            resetAdapter()
        }

        val nextButton = view.findViewById<Button>(R.id.nextButton)
        nextButton.setOnClickListener {
            dateManager.nextMonth()
            resetAdapter()
        }

        mList.setOnItemClickListener { _, _, i, _ ->
            val model = mList.adapter.getItem(i) as WorkingTimeModel
            val choices = arrayOf(
                    model.attendance ?: "出勤時間を登録",
                    model.leaving ?: "退勤時間を登録",
                    "キャンセル")
            AlertDialog.Builder(this.requireContext())
                    .setTitle("データの編集")
                    .setItems(choices){_, which ->
                        when(which){
                            0 -> editData(i, which, model.attendance)
                            1 -> editData(i, which, model.leaving)
                        }
                    }.show()
        }

        mList.setOnItemLongClickListener { _, _, i, _ ->
            val model = mList.adapter.getItem(i) as WorkingTimeModel
            if (model.attendance != null || model.leaving != null ) {
                AlertDialog.Builder(this.requireContext())
                        .setTitle("データの削除")
                        .setMessage("${model.date}の勤務データを削除しますか？")
                        .setPositiveButton("削除") { _, _ ->
                            (mList.adapter as CostomAdapter).remove(i)
                        }.setNegativeButton("やめる", null).show()
            }
            return@setOnItemLongClickListener true
        }

        return view
    }


    private fun resetAdapter() {
        (mList.adapter as CostomAdapter).reset(dateManager.days)
        titleText.text = dateManager.getMonth()
    }


    private fun editData(index: Int, type: Int, date: String?) {
        TimePickerDialog(this.requireContext(),{_, hour, minute ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val editedDate = SimpleDateFormat("hh:mm", Locale.US).format(cal.time)
            when(type){
                0 -> (mList.adapter as CostomAdapter).editAttendance(index, editedDate)
                1 -> (mList.adapter as CostomAdapter).editLeaving(index, editedDate)
            }
        },Integer.parseInt(date?.substring(0..1) ?: "12"), Integer.parseInt(date?.substring(3..4) ?: "00"), true)
                .show()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ListFragment().apply {}
    }
}
