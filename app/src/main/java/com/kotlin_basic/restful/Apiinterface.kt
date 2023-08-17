package com.only.restapi.RestApi

import androidx.room.DeleteTable
import com.google.gson.JsonElement
import com.idi.database.entity.HabitationEntity
import com.idi.database.entity.ScheduleEntity
import retrofit2.Call
import retrofit2.http.*

interface Apiinterface {

    @POST("api/signin")
    fun verifyMobileNumber(@Body hashmap:HashMap<String,String>):Call<JsonElement>

    @POST("api/sendOtp")
    fun sendOTP(@Body hashmap:HashMap<String,String>):Call<JsonElement>

    @POST("user/socialSignIn")
    fun resendOTP(@Body hashmap:HashMap<String,String>):Call<JsonElement>

    @GET("api/logout")
    fun logout(@QueryMap hashmap:HashMap<String,String>):Call<JsonElement>

    @POST("api/addhabitation")
    fun addHabitation(@Body habitationEntity: HabitationEntity):Call<JsonElement>

    @POST("api/addSchedule")
    fun addSchedule(@Body scheduleEntity: ScheduleEntity):Call<JsonElement>

    @POST("api/storeSurvey")
    fun storeSurvey(@Body hashmap:HashMap<String,Any>):Call<JsonElement>

    @GET
    fun getHabitationList(@Url url:String):Call<JsonElement>

    @GET
    fun getScheduleList(@Url url:String):Call<JsonElement>

    @GET
    fun getResponseList(@Url url:String):Call<JsonElement>

}