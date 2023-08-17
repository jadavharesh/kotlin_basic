package com.idi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.idi.database.entity.ResponseEntity
import com.idi.database.entity.HabitationEntity
import com.idi.database.entity.ScheduleEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.Executors


@Database(entities = arrayOf(HabitationEntity::class, ScheduleEntity::class, ResponseEntity::class), version = 5, exportSchema = false)
abstract class IDIRoomDatabase : RoomDatabase() {

    abstract fun iDIDao(): IDIDao

    companion object {
        @Volatile
        private var INSTANCE: IDIRoomDatabase? = null
        private const val NUMBER_OF_THREADS = 4
        val databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS)

        fun getDatabase(context: Context, scope: CoroutineScope): IDIRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    IDIRoomDatabase::class.java,
                    Constraint.Databasename
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(IDIDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class IDIDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase()
                    }
                }
            }
        }

        suspend fun populateDatabase() {

        }

    }

    private val sRoomDatabaseCallback: Callback = object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            databaseWriteExecutor.execute(
                Runnable {
                    val dao: IDIDao = INSTANCE?.iDIDao()!!
                })
        }
    }
}