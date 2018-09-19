package jp.co.apps.workout.workmanagement.fragment

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextClock
import android.widget.TextView
import jp.co.apps.workout.workmanagement.MyRealm
import jp.co.apps.workout.workmanagement.R
import jp.co.apps.workout.workmanagement.model.AttendanceModel
import jp.co.apps.workout.workmanagement.model.LeavingModel
import java.text.SimpleDateFormat
import java.util.*


class ClockFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clock, container, false)

        val attendanceButton = view.findViewById<Button>(R.id.attendanceButton)
        val attendanceText = view.findViewById<TextView>(R.id.attendanceText)
        val leavingLayout = view.findViewById<RelativeLayout>(R.id.leavingLayout)
        val clock = view.findViewById<TextClock>(R.id.clock)
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val today = format.format(Date())
        val attendStr: String? = MyRealm.searchAttend(today)

        attendStr?.let {
            attendanceText.text = it
            attendanceButton.visibility = View.INVISIBLE
            leavingLayout.visibility = View.VISIBLE
        }

        attendanceButton.setOnClickListener {
            val timeStr = clock.text.toString()
            attendanceText.text = timeStr
            MyRealm.insert(AttendanceModel(today, timeStr))
            it.visibility = View.INVISIBLE
            leavingLayout.visibility = View.VISIBLE
        }

        val attendanceTimeSetListener = TimePickerDialog.OnTimeSetListener{_, hour, minute ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val attend = SimpleDateFormat("hh:mm", Locale.US).format(cal.time)
            attendanceText.text = attend
            MyRealm.insert(AttendanceModel(today, attend))
        }

        attendanceText.setOnClickListener{
            val hour = Integer.parseInt(attendanceText.text.substring(0..1))
            val minute = Integer.parseInt(attendanceText.text.substring(3..4))
            TimePickerDialog(context, attendanceTimeSetListener, hour, minute, true).show()
        }

        val leavingButton = view.findViewById<Button>(R.id.leavingButton)
        val leavingText = view.findViewById<TextView>(R.id.leavingText)
        val leavingStr: String? = MyRealm.searchLeaving(today)

        leavingStr?.let {
            leavingText.text = it
            leavingButton.visibility = View.INVISIBLE
        }

        leavingButton.setOnClickListener {
            val timeStr = clock.text.toString()
            leavingText.text = timeStr
            MyRealm.insert(LeavingModel(today, timeStr))
            it.visibility = View.INVISIBLE
        }

        val leavingTimeSetListener = TimePickerDialog.OnTimeSetListener{_, hour, minute ->
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            val leaving = SimpleDateFormat("hh:mm", Locale.US).format(cal.time)
            leavingText.text = leaving
            MyRealm.insert(LeavingModel(today, leaving))
        }

        leavingText.setOnClickListener{
            val hour = Integer.parseInt(leavingText.text.substring(0..1))
            val minute = Integer.parseInt(leavingText.text.substring(3..4))
            TimePickerDialog(context, leavingTimeSetListener, hour, minute, true).show()
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ClockFragment().apply {}
    }
}
