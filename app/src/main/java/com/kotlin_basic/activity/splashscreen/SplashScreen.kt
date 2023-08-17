package com.idi.activity.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.idi.R
import com.idi.activity.login.Login
import com.idi.activity.main.MainActivity
import com.idi.activity.schedule.ScheduleactivityViewmodel
import com.idi.activity.schedule.ScheduleactivityViewmodelprovider
import com.idi.comman.BaseActivity2
import com.idi.databinding.ActivitySplashScreenBinding
import com.idi.utility.Idimain
import com.only.restapi.Utility.Prefesmanager
import com.skyautonet.dtglite.bbiapp.Utils.AppConstants

class SplashScreen : BaseActivity2<ActivitySplashScreenBinding>(
layoutResourceId = R.layout.activity_splash_screen
) {

    private val viewModel : SplashViewModel by viewModels {
        SplashViewmodelprovider((application as Idimain).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        viewModel.gotoNesxt.observe(this)
        {
            if (it){
                if(Prefesmanager.containKey(Prefesmanager.Token)!!)
                {
                    val intentMain=Intent(this, MainActivity::class.java)
                    intentMain.putExtra(AppConstants.isFromLogin,false)
                    startActivity(intentMain)
                    finishAffinity()

                }else
                {
                    val intentLogin=Intent(this, Login::class.java)
                    startActivity(intentLogin)
                    finishAffinity()
                }
            }
        }
    }

    private fun initView()
    {
        viewDataBinding.run {
            vm = viewModel
            activity = this@SplashScreen
        }
    }
}