package jp.co.apps.workout.workmanagement.model

import jp.co.apps.workout.workmanagement.MyRealm

data class WorkingTimeModel(val date: String, var attendance: String? = null, var leaving: String? = null) {

    init {
        attendance = MyRealm.searchAttend(date)
        leaving = MyRealm.searchLeaving(date)
    }
}