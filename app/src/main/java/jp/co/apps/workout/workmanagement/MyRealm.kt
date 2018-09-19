package jp.co.apps.workout.workmanagement

import io.realm.Realm
import io.realm.RealmObject
import jp.co.apps.workout.workmanagement.model.AttendanceModel
import jp.co.apps.workout.workmanagement.model.LeavingModel

object MyRealm {

    
    fun insert(obj: RealmObject) {
        val mRealm = Realm.getDefaultInstance()
        mRealm.use {
            it.beginTransaction()
            it.copyToRealmOrUpdate(obj)
            it.commitTransaction()
        }
    }

    fun searchAttend(date: String): String?{
        val mRealm = Realm.getDefaultInstance()
        mRealm.use {
            return it.where(AttendanceModel::class.java).equalTo("date", date).findFirst()?.time
        }
    }

    fun searchLeaving(date: String): String? {
        val mRealm = Realm.getDefaultInstance()
        mRealm.use {
            return it.where(LeavingModel::class.java).equalTo("date", date).findFirst()?.time
        }
    }

    fun removeData(date: String) {
        val mRealm = Realm.getDefaultInstance()
        mRealm.use {
            val attend = it.where(AttendanceModel::class.java).equalTo("date", date).findAll()
                    ?: null
            val leaving = it.where(LeavingModel::class.java).equalTo("date", date).findAll()
                    ?: null
            it.beginTransaction()
            attend?.deleteAllFromRealm()
            leaving?.deleteAllFromRealm()
            it.commitTransaction()
        }
    }
}