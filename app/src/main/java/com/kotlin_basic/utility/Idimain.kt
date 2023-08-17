package com.idi.utility

import android.app.Application
import android.content.Context
import com.idi.database.IDIRepository
import com.idi.database.IDIRoomDatabase
import com.idi.database.repository.HabitationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Idimain : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { IDIRoomDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { IDIRepository(database.iDIDao()) }

    companion object
    {
        private var  appContext: Context?=null
        fun getContext(): Context?{
            return appContext;
        }
    }

    override fun onCreate() {
        super.onCreate()
        setAppContext(applicationContext)
    }

    private fun setAppContext(context: Context)
    {
        appContext=context
    }
}