package jp.co.apps.workout.workmanagement.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import jp.co.apps.workout.workmanagement.R
import jp.co.apps.workout.workmanagement.fragment.ClockFragment
import jp.co.apps.workout.workmanagement.fragment.ListFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentManager = supportFragmentManager

        when (item.itemId) {
            R.id.navigation_clock -> {
                fragmentManager.beginTransaction().replace(R.id.container, ClockFragment.newInstance()).commit()
                title = "勤怠打刻"
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_list -> {
                fragmentManager.beginTransaction().replace(R.id.container, ListFragment.newInstance()).commit()
                title = "勤務履歴"
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.container, ClockFragment.newInstance()).commit()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        title = "勤怠打刻"

    }

}
