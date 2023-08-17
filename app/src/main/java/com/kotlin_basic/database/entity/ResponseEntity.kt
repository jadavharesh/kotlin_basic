package com.idi.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "response",
    foreignKeys = arrayOf(
        /*ForeignKey(
            entity = HabitationEntity::class,
            childColumns = ["iFk_hebitationId"],
            parentColumns = ["id_hb"]
        ),
        ForeignKey(
            entity = ScheduleEntity::class,
            childColumns = ["iFK_scheduleId"],
            parentColumns = ["id_schedule"]
        )*/
    )
)
data class ResponseEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_response") var id_response:Int?,
    @ColumnInfo(name = "iFkuser_id") val iFkuser_id:Int?,
    @ColumnInfo(name = "iFk_hebitationId") val iFk_hebitationId:Int?,
    @ColumnInfo(name = "iFK_scheduleId") val iFK_scheduleId:Int?,
    @ColumnInfo(name = "name") val name:String?,
    @ColumnInfo(name = "age") val age:Int?,
    @ColumnInfo(name = "mobile") val mobile:String?,
    @ColumnInfo(name = "gender") val gender:String?,
    @ColumnInfo(name = "profession") val profession:String?,
    @ColumnInfo(name = "religion") val religion:String?,
    @ColumnInfo(name = "category") val category:String?,
    @ColumnInfo(name = "caste") val caste:String?,
    @ColumnInfo(name = "education") val education:String?,
    @ColumnInfo(name = "area") val area:String?,
    @ColumnInfo(name = "designation") val designation:String?,
    @ColumnInfo(name = "fullAddress") val fullAddress:String?,
    @ColumnInfo(name = "landmark") val landmark:String?,
    @ColumnInfo(name = "villageTown") val villageTown:String?,
    @ColumnInfo(name = "remarks") val remarks:String?,
    @ColumnInfo(name = "community_Health_Center") val community_Health_Center:String?,
    @ColumnInfo(name = "primary_Health_Center") val primary_Health_Center:String?,
    @ColumnInfo(name = "health_Sub_Center") val health_Sub_Center:String?,
    @ColumnInfo(name = "panchayat_Bhavan") val panchayat_Bhavan:String?,
    @ColumnInfo(name = "community_Hall") val community_Hall:String?,
    @ColumnInfo(name = "recreational_Facility") val recreational_Facility:String?,
    @ColumnInfo(name = "burial_Cremation_Ground") val burial_Cremation_Ground:String?,
    @ColumnInfo(name = "community_Public_Toilet") val community_Public_Toilet:String?,
    @ColumnInfo(name = "public_Bus_Service") val public_Bus_Service:String?,
    @ColumnInfo(name = "police_Station_OP") val police_Station_OP:String?,
    @ColumnInfo(name = "govt_Primary_School") val govt_Primary_School:String?,
    @ColumnInfo(name = "govt_Middle_School") val govt_Middle_School:String?,
    @ColumnInfo(name = "govt_High_School") val govt_High_School:String?,
    @ColumnInfo(name = "govt_Intermediate_School") val govt_Intermediate_School:String?,
    @ColumnInfo(name = "mid_Day_Meal") val mid_Day_Meal:String?,
    @ColumnInfo(name = "performance") val performance:String?,
    @ColumnInfo(name = "txtPerformance") val txtPerformance:String?,
    @ColumnInfo(name = "satisfaction") val satisfaction:String?,
    @ColumnInfo(name = "txtSatisfaction") val txtSatisfaction:String?,

    @ColumnInfo(name = "stepbSection2") val stepbSection2:String?,
    @ColumnInfo(name = "stepbSection2second") val stepbSection2second:String?,

    @ColumnInfo(name = "performance_Rating_of_Central_GovtModi") val performance_Rating_of_Central_GovtModi:String?,
    @ColumnInfo(name = "txtratingPerformance") val txtratingPerformance:String?,
    @ColumnInfo(name = "contribution_of_Central_Govt_in_Development_of_Bihar") val contribution_of_Central_Govt_in_Development_of_Bihar:String?,
    @ColumnInfo(name = "txtContribution") val txtContribution:String?,
    @ColumnInfo(name = "performance_Rating_of_Central_GovtModi_Year") val performance_Rating_of_Central_GovtModi_Year:String?,
    @ColumnInfo(name = "txtyearPerformance") val txtyearPerformance:String?,
    @ColumnInfo(name = "txtyearContribution") val txtyearContribution:String?,
    @ColumnInfo(name = "tejaswi_As_a_Strong_Leader") val tejaswi_As_a_Strong_Leader:String?,
    @ColumnInfo(name = "txtTejaswiStrongLeader") val txtTejaswiStrongLeader:String?,
    @ColumnInfo(name = "tejaswi_Role_in_2025") val tejaswi_Role_in_2025:String?,
    @ColumnInfo(name = "txtTejaswiRole") val txtTejaswiRole:String?,
    @ColumnInfo(name = "satisfaction_With_Alliance") val satisfaction_With_Alliance:String?,
    @ColumnInfo(name = "txtSatisfactionWithAlliance") val txtSatisfactionWithAlliance:String?,
    @ColumnInfo(name = "stability_of_Government") val stability_of_Government:String?,
    @ColumnInfo(name = "stabilityofGovernment") val stabilityofGovernment:String?,

    @ColumnInfo(name = "stepcSection1") val stepcSection1:String?,

    @ColumnInfo(name = "stepdSection1") val stepdSection1:String?,
    @ColumnInfo(name = "stepdSection2") val stepdSection2:String?,
    @ColumnInfo(name = "stepdSection3") val stepdSection3:String?,

    @ColumnInfo(name = "stepeSection1") val stepeSection1:String?,
    @ColumnInfo(name = "stepeSection2") val stepeSection2:String?,
    @ColumnInfo(name = "stepeSection3") val stepeSection3:String?,

    @ColumnInfo(name = "stepfsection1Question") val stepfsection1Question:String?,
    @ColumnInfo(name = "txtstepfsection1Remark") val txtstepfsection1Remark:String?,
    @ColumnInfo(name = "stepfsection2Question") val stepfsection2Question:String?,
    @ColumnInfo(name = "stepfsection3Question") val stepfsection3Question:String?,
    @ColumnInfo(name = "stepfsection2dropdown") val stepfsection2dropdown:String?,
    @ColumnInfo(name = "stepfsection3dropdown") val stepfsection3dropdown:String?,


    @ColumnInfo(name = "status") var status:String?,
    @ColumnInfo(name = "uploaded") var uploaded:Boolean?,
    var isSelected:Boolean=false,
)
