package com.only.restapi.Utility

import android.R.attr
import android.content.Context
import android.content.SharedPreferences
import android.R.attr.value

import android.R.attr.key
import com.idi.utility.Idimain

class Prefesmanager {

    companion object
    {
        private const val SP_NAME:String="PREF"
        const val Token="TOKEN"
        const val Name="Name"
        const val Email="Email"
        const val Mobile="Mobile"
        const val Profile="Profile"
        const val UserId="UserId"



        private fun getSharedPreferences(context: Context): SharedPreferences? {
            return context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }

        fun savePrefsVal(key:String,value:String)
        {
            val editor= getSharedPreferences(Idimain.getContext()!!)?.edit()
            editor?.putString(key,value)
            editor?.commit()
        }
        fun savePrefsVal(key:String,value:Int)
        {
            val editor= getSharedPreferences(Idimain.getContext()!!)?.edit()
            editor?.putInt(key,value)
            editor?.commit()
        }
        fun savePrefsVal(key:String,value:Float)
        {
            val editor= getSharedPreferences(Idimain.getContext()!!)?.edit()
            editor?.putFloat(key,value)
            editor?.commit()
        }
        fun savePrefsVal(key:String,value:Boolean)
        {
            val editor= getSharedPreferences(Idimain.getContext()!!)?.edit()
            editor?.putBoolean(key,value)
            editor?.commit()
        }

        fun readStringPrefVal(key:String): String? {
            return getSharedPreferences(Idimain.getContext()!!)?.getString(key,null)
        }
        fun readBooleanPrefVal(key:String): Boolean? {
            return getSharedPreferences(Idimain.getContext()!!)?.getBoolean(key,false)
        }
        fun readFloatPrefVal(key:String): Float? {
            return getSharedPreferences(Idimain.getContext()!!)?.getFloat(key, 0.0F)
        }
        fun readIntPrefVal(key:String): Int? {
            return getSharedPreferences(Idimain.getContext()!!)?.getInt(key, 0)
        }
        fun containKey(key: String): Boolean? {
           return getSharedPreferences(Idimain.getContext()!!)?.contains(key)
        }
        fun removeKey(key: String)
        {
            val editor= getSharedPreferences(Idimain.getContext()!!)?.edit()
            editor?.remove(key)
        }
        fun removeAllKey()
        {
            getSharedPreferences(Idimain.getContext()!!)?.edit()?.clear()?.apply()
        }
    }

}