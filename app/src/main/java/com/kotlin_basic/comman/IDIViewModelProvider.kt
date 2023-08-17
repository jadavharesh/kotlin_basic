package com.idi.comman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.idi.database.IDIRepository
import com.idi.fragment.schedule.ScheduleViewmodel
import java.lang.IllegalArgumentException

class IDIViewModelProvider(private val repository : IDIRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        /*if(modelClass.isAssignableFrom(ScheduleViewmodel::class.java))
        {
            return ScheduleViewmodel(repository) as T
        }*/
        throw IllegalArgumentException("Viewmodel class not found..")
    }
}