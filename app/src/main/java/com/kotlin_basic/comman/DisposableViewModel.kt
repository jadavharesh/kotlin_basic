package com.skyautonet.dtglite.bbiapp.comman

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class DisposableViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    init {
        clearCompositeDisposable()
    }

    protected fun addDisposables(vararg disposable: Disposable) {
        compositeDisposable.addAll(*disposable)
    }

    protected fun Disposable.disposeOnClear(): Disposable {
        ///addTo(compositeDisposable)
        return this
    }

    protected fun clearCompositeDisposable() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        Log.d("viewmodel","ViewModel Cleared : ${this.javaClass.canonicalName}")
        clearCompositeDisposable()
        super.onCleared()
    }

}