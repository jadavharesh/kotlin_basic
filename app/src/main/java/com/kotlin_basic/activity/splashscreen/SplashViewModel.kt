package com.idi.activity.splashscreen

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idi.activity.schedule.ScheduleactivityViewmodel
import com.idi.database.IDIRepository
import com.idi.utility.Event
import com.skyautonet.dtglite.bbiapp.comman.ToolbarViewModel
import java.lang.IllegalArgumentException

data class SplashViewModel(
    private val idiRepository: IDIRepository,
    private val _error: MutableLiveData<String> = MutableLiveData(),//error message
    val isShowDialog: MutableLiveData<Boolean> = MutableLiveData(),//show progres dialog
    val setMessage: MutableLiveData<Event<String>> = MutableLiveData(),//api response message
    val gotoNesxt:MutableLiveData<Boolean> = MutableLiveData(false),
) :ToolbarViewModel()
{
    init {
       gotonext()
    }

    private fun gotonext()
    {
        Handler(Looper.getMainLooper()).postDelayed({
            gotoNesxt.postValue(true)
        }, 2000)

    }
}
class SplashViewmodelprovider(private val repository : IDIRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(SplashViewModel::class.java))
        {
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Viewmodel class not found..")
    }
}
