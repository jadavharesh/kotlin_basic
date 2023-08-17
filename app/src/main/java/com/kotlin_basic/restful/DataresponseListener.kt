package com.only.restapi.RestApi

interface DataresponseListener {
    fun onSuccessresponse(response:String)
    fun onFailure()
}