package com.only.restapi.RestApi

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Callback
import java.util.*
import android.util.Log

import androidx.annotation.NonNull
import retrofit2.Response
import com.google.gson.Gson
import com.idi.fragment.response.CommanResponse


class GeneralRetrofit(call: Call<JsonElement>, params:Any?, dataresponseListener: DataresponseListener) {

    private val call: Call<JsonElement>? = call
    private val params: Any? = params
    private var dataResponseListener: DataresponseListener? = dataresponseListener

    fun call(): Call<JsonElement>? {
        Log.w("Inside Class", "--->   " + " API URL = " + call!!.request().url)
        if (params != null && call.request().body != null) {
            Log.w("Inside Class", "--->   " + " Passing Params = " + Gson().toJson(params))
        }
        call.enqueue(postCall)
        return call
    }

    private val postCall: Callback<JsonElement?> = object : Callback<JsonElement?> {
        override fun onResponse(call: Call<JsonElement?>, response: Response<JsonElement?>) {
            Log.w("Inside Class", "--->   " + " Status code : " + response.code())
            if (response.code() !== 200) {
                Log.w("Inside Class", "--->   " + " Response NOT OK : " + response.raw().message)
            }

            var responseString: String? = null
            if (response.body() != null) {
                Log.w("Inside Class", "--->   " + " RESPONSE = " + java.lang.String.valueOf(response.body()))
                responseString = java.lang.String.valueOf(response.body())

               /* val commanResponse: CommanResponse = Gson().fromJson(response, CommanResponse::class.java)
                if(commanResponse.success)
                {

                }*/

                if (dataResponseListener != null) dataResponseListener!!.onSuccessresponse(responseString)
            } else {
                if (dataResponseListener != null) dataResponseListener!!.onFailure()
            }
        }

        override fun onFailure(call: Call<JsonElement?>, t: Throwable) {
            Log.w("Inside Class", "--->    ON_Failure = $t")
            Log.w("Inside Class", "--->   " + " ON_Failure Localize Message = " + t.localizedMessage)
            Log.w("Inside Class", "--->   " + " ON_Failure Message = " + t.message)
            if (!call.isCanceled) if (dataResponseListener != null) dataResponseListener!!.onFailure()
        }
    }
}