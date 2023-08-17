package com.idi.database

import androidx.annotation.WorkerThread
import com.idi.database.entity.ResponseEntity
import com.idi.database.entity.HabitationEntity
import com.idi.database.entity.ScheduleEntity
import com.only.restapi.Utility.Prefesmanager
import kotlinx.coroutines.flow.Flow

class IDIRepository(private val idiDao: IDIDao) {

    val habitationEntityList : Flow<List<HabitationEntity>> = idiDao.getHabitationList()
    val scheduleEntityList : Flow<List<ScheduleEntity>> = idiDao.getScheduleList()
    val responseEntityList : Flow<List<ResponseEntity>> = idiDao.getResponseList()


    fun getAllHabitationList():List<HabitationEntity> {
        return idiDao.getAllHabitationList()
    }

    fun getScheduleListById(habitation_id:Int):List<ScheduleEntity> {
        return idiDao.getScheduleListById()
    }

    fun getScheduleListByhabitation(habitation:String):List<ScheduleEntity> {
        return idiDao.getScheduleListByhabitation(habitation)
    }

    fun getScheduleListByhabitationandresponded(habitation:String):List<ScheduleEntity> {
        return idiDao.getScheduleListByhabitationandresponded(habitation,false)
    }

    fun getResponseListById(habitation_id:Int):List<ResponseEntity> {
        return idiDao.getResponseListById(habitation_id)
    }

    fun getScheduleById(id_schedule:Int):ScheduleEntity {
        return idiDao.getScheduleById(id_schedule)
    }

    fun getHabitationById(id_hb:Int):HabitationEntity {
        return idiDao.getHabitationById(id_hb)
    }

    fun getResponseById(id_response:Int):ResponseEntity
    {
        return idiDao.getResponseById(id_response)
    }

    fun updateschedule(scheduleEntity: ScheduleEntity)
    {
        return idiDao.updateschedule(scheduleEntity)
    }

    fun edithabitation(habitationEntity: HabitationEntity)
    {
        return idiDao.edithabitation(habitationEntity)
    }

    fun updateresponse(responseEntity: ResponseEntity)
    {
        return idiDao.updateresponse(responseEntity)
    }

    fun updateResponsestatus(id_response:Int,status:String)
    {
        return idiDao.updateResponsestatus(id_response,status)
    }

    fun deleteTableHabitation() {
        idiDao.allHabitationremove()
    }

    fun deleteTableSchedule(){
        idiDao.allscheduleremove()
    }

    fun deleteTableResponse(){
        idiDao.AllResponseremove()
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addHabitation(habitationEntity: HabitationEntity):Long {
        return idiDao.addhabitation(habitationEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addHabitationList(habitationList: List<HabitationEntity>) {
        return idiDao.addhabitationList(habitationList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addSchedule(scheduleEntity: ScheduleEntity):Long {
        return idiDao.addschedule(scheduleEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addScheduleList(scheduleList: List<ScheduleEntity>) {
        return idiDao.addscheduleList(scheduleList)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addResponse(responseEntity: ResponseEntity):Long {
         return idiDao.addResponse(responseEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addResponseList(responseList: List<ResponseEntity>) {
        return idiDao.addResponseList(responseList)
    }

   /* fun getHabitationList():List<Habitation> {
       return idiDao.getHabitationList()
    }*/

}