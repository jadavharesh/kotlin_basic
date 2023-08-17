package com.idi.utility

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable

import io.reactivex.Single


fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> {
    return this
}

