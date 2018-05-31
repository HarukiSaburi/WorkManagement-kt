package jp.co.apps.workout.workmanagement

import io.realm.Realm
import jp.co.apps.workout.workmanagement.model.AttendanceModel
import jp.co.apps.workout.workmanagement.model.LeavingModel

object MyRealm {

    val mRealm: Realm

    init {
        mRealm = Realm.getDefaultInstance()
    }

    fun close() {
        mRealm.close()
    }

    fun insertAttend(date: String, time: String) {
        mRealm.beginTransaction()
        val attend = mRealm.where(AttendanceModel::class.java).equalTo("date", date).findFirst() ?: mRealm.createObject(AttendanceModel::class.java, date)
        attend.time = time
        mRealm.copyToRealmOrUpdate(attend)
        mRealm.commitTransaction()
    }

    fun insertLeaving(date: String, time: String){
        mRealm.beginTransaction()
        val leaving = mRealm.where(LeavingModel::class.java).equalTo("date", date).findFirst() ?: mRealm.createObject(LeavingModel::class.java, date)
        leaving.time = time
        mRealm.copyToRealmOrUpdate(leaving)
        mRealm.commitTransaction()
    }

    fun searchAttend(date: String): String?{
        val result = mRealm.where(AttendanceModel::class.java).equalTo("date", date).findFirst()
        return result?.time
    }

    fun searchLeaving(date: String): String? {
        val result = mRealm.where(LeavingModel::class.java).equalTo("date", date).findFirst()
        return result?.time
    }

    fun removeData(date: String) {
        val attend = mRealm.where(AttendanceModel::class.java).equalTo("date", date).findAll() ?: null
        val leaving = mRealm.where(LeavingModel::class.java).equalTo("date", date).findAll() ?: null
        mRealm.beginTransaction()
        attend?.deleteAllFromRealm()
        leaving?.deleteAllFromRealm()
        mRealm.commitTransaction()
    }
}