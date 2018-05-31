package jp.co.apps.workout.workmanagement.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LeavingModel(
        @PrimaryKey open var date: String = "",
        open var time: String = ""
): RealmObject()