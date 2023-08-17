package com.idi.utility

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Event<T>(value: T) {
    var value = value
        private set

    private var isAlreadyHandled = false

    fun isValid(): Boolean = if (isAlreadyHandled) {
        false
    } else {
        isAlreadyHandled = true
        true
    }
}

fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: Observer<T>) =
    observe(owner) {
        if (it.isValid()) {
            observer.onChanged(it.value)
        }
    }

fun MutableLiveData<Event<Unit>>.emitEvent() {
    postValue(Event(Unit))
}

fun <T> MutableLiveData<Event<T>>.emitEvent(value: T) {
    postValue(Event(value))
}
