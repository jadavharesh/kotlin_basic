package com.idi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigInteger

/*@Entity(tableName = "habitation")
data class Habitation (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name:String=""
)*/

@Entity(tableName = "habitation")
data class HabitationEntity (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_hb") val id_hb:Int?,
    @ColumnInfo(name = "unique_id") val unique_id:String?,
    @ColumnInfo(name = "iFkuser_id") val iFkuser_id:Int?,
    @ColumnInfo(name = "hb_name") val hb_name:String?,
    @ColumnInfo(name = "hb_area") val hb_area:String?,
    @ColumnInfo(name = "hb_district") val hb_district:String?,
    @ColumnInfo(name = "hb_ac") val hb_ac:String?,
    @ColumnInfo(name = "hb_blk_twn") val hb_blk_twn:String?,
    @ColumnInfo(name = "hb_municipality") val hb_municipality:String?,
    @ColumnInfo(name = "hb_pncht") val hb_pncht:String?,
    @ColumnInfo(name = "hb_totl_popl") val hb_totl_popl:Long?,
    @ColumnInfo(name = "hb_totl_vtr") val hb_totl_vtr:Long?,
    @ColumnInfo(name = "hb_hindu") val hb_hindu:Long?,
    @ColumnInfo(name = "hb_muslim") val hb_muslim:Long?,
    @ColumnInfo(name = "hb_sc") val hb_sc:Long?,
    @ColumnInfo(name = "hb_st") val hb_st:Long?,
    @ColumnInfo(name = "hb_women") val hb_women:Long?,
    @ColumnInfo(name = "hb_literacy") val hb_literacy:Double?,
    @ColumnInfo(name = "created_at") val created_at:String?,
    @ColumnInfo(name = "updated_at") val updated_at:String?,
    @ColumnInfo(name = "uploaded") var uploaded:Boolean?,
    var isVisible:Boolean=false,
    )
{
    override fun toString(): String = hb_name!!
}
