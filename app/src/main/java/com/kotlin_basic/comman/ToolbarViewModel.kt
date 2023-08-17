package com.skyautonet.dtglite.bbiapp.comman

import androidx.lifecycle.MutableLiveData
import com.idi.utility.Event
import com.idi.utility.emitEvent
import com.idi.utility.toLiveData


open class ToolbarViewModel (
    val title : MutableLiveData<String> = MutableLiveData(),
    val showStatus: MutableLiveData<Boolean> = MutableLiveData(),
    val isTaskRoot: MutableLiveData<Boolean> = MutableLiveData(),
    private val _onBackPressed: MutableLiveData<Event<Unit>> = MutableLiveData(),
    private val _onFinish: MutableLiveData<Event<Unit>> = MutableLiveData()
): DisposableViewModel(){

    val onBackPressed = _onBackPressed.toLiveData()
    val onFinish = _onFinish.toLiveData()

    fun onBackPressed() {
        _onBackPressed.emitEvent()
    }

    fun onFinish() {
        _onFinish.emitEvent()
    }

}