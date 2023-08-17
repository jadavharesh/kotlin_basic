package com.only.restapi.RestApi

import android.os.Build
import com.idi.database.entity.HabitationEntity
import com.idi.database.entity.ScheduleEntity
import com.only.restapi.Utility.Prefesmanager
import java.util.*


class ApiClass {

    companion object
    {
        val apiInterface = Restclient.getApiInterface()

        /*fun verifyMobileNumber(mobile_number:String,dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, String> ()

            hashMap.put("mobile",mobile_number)
            //hashMap.put("deviceName", Build.MODEL.toString())
            //hashMap.put("device_version", Build.VERSION.RELEASE)

            GeneralRetrofit(apiInterface!!.verifyMobileNumber(hashMap),hashMap,dataResponseListener!!).call()
        }*/

        fun verifyMobileNumber(otp:String,dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, String> ()

            hashMap.put("otp",otp)
            GeneralRetrofit(apiInterface!!.verifyMobileNumber(hashMap),hashMap,dataResponseListener!!).call()
        }

        fun sendOTP(mobile_number:String,dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, String> ()

            hashMap.put("mobile",mobile_number)

            GeneralRetrofit(apiInterface!!.sendOTP(hashMap),hashMap,dataResponseListener!!).call()
        }

        fun resendOTP(OTP:String,mobile_number:String,dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, String> ()

            hashMap.put("mobile_number",mobile_number)

            GeneralRetrofit(apiInterface!!.resendOTP(hashMap),hashMap,dataResponseListener!!).call()
        }

        fun logout(dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, String> ()
            hashMap.put("token",Prefesmanager.readStringPrefVal(Prefesmanager.Token).toString())

            GeneralRetrofit(apiInterface!!.logout(hashMap),hashMap,dataResponseListener!!).call()
        }

        fun addHabitation(habitationEntity: HabitationEntity, dataResponseListener: DataresponseListener?) {

            GeneralRetrofit(apiInterface!!.addHabitation(habitationEntity),null,dataResponseListener!!).call()
        }

        fun addSchedule(scheduleEntity: ScheduleEntity, dataResponseListener: DataresponseListener?) {

            GeneralRetrofit(apiInterface!!.addSchedule(scheduleEntity),null,dataResponseListener!!).call()
        }

        fun storeSurvey(schedule_id:Int?,id_response:Int,response:String,dataResponseListener: DataresponseListener?) {

            var hashMap = HashMap<String, Any> ()
            hashMap.put("user_id", Prefesmanager.readIntPrefVal(Prefesmanager.UserId)!!)
            hashMap.put("iFk_scheduleid", if(schedule_id!=null) schedule_id else "")
            hashMap.put("id_response",id_response)
            hashMap.put("user_response",response)

            GeneralRetrofit(apiInterface!!.storeSurvey(hashMap),hashMap,dataResponseListener!!).call()
        }

        fun getHabitationList(dataResponseListener: DataresponseListener?)
        {
            val url:String="api/getHabitation/"+Prefesmanager.readIntPrefVal(Prefesmanager.UserId)
            GeneralRetrofit(apiInterface!!.getHabitationList(url),null,dataResponseListener!!).call()
        }

        fun getScheduleList(dataResponseListener: DataresponseListener?)
        {
            val url:String="api/getSchedule/"+Prefesmanager.readIntPrefVal(Prefesmanager.UserId)
            GeneralRetrofit(apiInterface!!.getScheduleList(url),null,dataResponseListener!!).call()
        }

        fun getResponseList(dataResponseListener: DataresponseListener?)
        {
            val url:String="api/getAllSurvey/"+Prefesmanager.readIntPrefVal(Prefesmanager.UserId)
            GeneralRetrofit(apiInterface!!.getResponseList(url),null,dataResponseListener!!).call()
        }
    }
}