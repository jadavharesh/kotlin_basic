package com.idi.restful

import android.content.Context
import android.content.Intent
import com.idi.activity.splashscreen.SplashScreen
import com.idi.utility.Idimain
import com.only.restapi.Utility.Prefesmanager
import okhttp3.Interceptor
import okhttp3.Response

class UnauthorisedInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 401) {
            val context: Context = Idimain.getContext()!!
            val intent = Intent(context, SplashScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            Prefesmanager.removeAllKey()
            context.startActivity(intent)
        }
        return response
    }
}