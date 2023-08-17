package com.idi.database.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.idi.database.entity.HabitationEntity
import com.idi.database.IDIDao

class HabitationRepository(requireContext: Context) {

    private var idiDao: IDIDao? = null
    private var list: LiveData<List<HabitationEntity?>?>? = null

   /* @SuppressLint("NotConstructor")
    fun HabitationRepository(context: Context) {
        val db: IDIRoomDatabase = IDIRoomDatabase.getDatabase(context!!)
        idiDao = db.iDIDao()
        list = idiDao!!.getList()
    }*/

    fun getAllList(): LiveData<List<HabitationEntity?>?>? {
        return list
    }

   /* fun insert(habitation: Habitation) {
        IDIRoomDatabase.databaseWriteExecutor.execute { idiDao.insert(habitation) }
    }*/

}