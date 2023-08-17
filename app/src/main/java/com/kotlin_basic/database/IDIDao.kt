package com.idi.database

import androidx.room.*
import com.idi.database.entity.ResponseEntity
import com.idi.database.entity.HabitationEntity
import com.idi.database.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IDIDao {

    @Query("SELECT * FROM habitation")
    fun getHabitationList(): Flow<List<HabitationEntity>>

    @Query("SELECT * FROM schedule")
    fun getScheduleList(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM response")
    fun getResponseList(): Flow<List<ResponseEntity>>

    @Query("SELECT * FROM habitation")
    fun getAllHabitationList(): List<HabitationEntity>

    @Query("SELECT * FROM schedule")
    fun getScheduleListById(): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE hb_name=:habitation")
    fun getScheduleListByhabitation(habitation:String): List<ScheduleEntity>

    @Query("SELECT * FROM schedule WHERE municipality=:habitation AND isSchedule=:isSchedule OR pncht_ward=:habitation AND isSchedule=:isSchedule")
    fun getScheduleListByhabitationandresponded(habitation:String,isSchedule:Boolean): List<ScheduleEntity>

    @Query("SELECT * FROM response WHERE iFk_hebitationId =:habitation_id")
    fun getResponseListById(habitation_id:Int): List<ResponseEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addhabitation(habitationEntity: HabitationEntity):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addhabitationList(habitationList: List<HabitationEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addschedule(scheduleEntity: ScheduleEntity):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addscheduleList(scheduleList: List<ScheduleEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResponse(responseEntity: ResponseEntity):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addResponseList(responseList: List<ResponseEntity>)

    @Query("SELECT * FROM schedule WHERE id_schedule =:id_schedule")
    fun getScheduleById(id_schedule:Int): ScheduleEntity

    @Query("SELECT * FROM habitation WHERE id_hb =:id_hb")
    fun getHabitationById(id_hb:Int): HabitationEntity

    @Query("SELECT * FROM response WHERE id_response =:id_response")
    fun getResponseById(id_response:Int): ResponseEntity

    @Update
    fun updateschedule(vararg scheduleEntity: ScheduleEntity)

    @Update
    fun edithabitation(vararg habitationEntity: HabitationEntity)

    @Update
    fun updateresponse(vararg responseEntity: ResponseEntity)

    @Query("UPDATE response SET status = :status WHERE id_response = :id_response")
    fun updateResponsestatus(id_response:Int,status:String)

    @Query("DELETE FROM habitation")
    fun allHabitationremove()

    @Query("DELETE FROM schedule")
    fun allscheduleremove()

    @Query("DELETE FROM response")
    fun AllResponseremove()

}