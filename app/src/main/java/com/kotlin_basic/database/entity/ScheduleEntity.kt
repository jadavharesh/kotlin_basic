package com.idi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class ScheduleEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_schedule") var id_schedule:Int?,
    @ColumnInfo(name = "respondent_name") val respondent_name:String?,
    @ColumnInfo(name = "iFkuser_id") val iFkuser_id:Int?,
    @ColumnInfo(name = "hb_name") val hb_name:String?,
    @ColumnInfo(name = "gender") val gender:String?,
    @ColumnInfo(name = "age") val age:Int?,
    @ColumnInfo(name = "mobile") val mobile:String?,
    @ColumnInfo(name = "profession") val profession:String?,
    @ColumnInfo(name = "religion") val religion:String?,
    @ColumnInfo(name = "category") val category:String?,
    @ColumnInfo(name = "caste") val caste:String?,
    @ColumnInfo(name = "education") val education:String?,
    @ColumnInfo(name = "district") val district:String?,
    @ColumnInfo(name = "ac") val ac:String?,
    @ColumnInfo(name = "area") val area:String?,
    @ColumnInfo(name = "block_town") val block_town:String?,
    @ColumnInfo(name = "municipality") val municipality:String?,
    @ColumnInfo(name = "pncht_ward") val pncht_ward:String?,
    @ColumnInfo(name = "designation") val designation:String?,
    @ColumnInfo(name = "full_address") val full_address:String?,
    @ColumnInfo(name = "landmark") val landmark:String?,
    @ColumnInfo(name = "vlg_twn_name") val vlg_twn_name:String?,
    @ColumnInfo(name = "remarks") val remarks:String?,
    @ColumnInfo(name = "created_at") val created_at:String?,
    @ColumnInfo(name = "updated_at") val updated_at:String?,
    @ColumnInfo(name = "uploaded") var uploaded:Boolean?,
    @ColumnInfo(name = "isSchedule") var isSchedule:Boolean?,
    )
{
     override fun toString(): String = if(respondent_name!=null) respondent_name!! else ""
}

