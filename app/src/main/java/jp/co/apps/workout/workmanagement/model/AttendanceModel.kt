package jp.co.apps.workout.workmanagement.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class AttendanceModel(
        @PrimaryKey open var date: String = "",
        open var time: String = ""
): RealmObject()