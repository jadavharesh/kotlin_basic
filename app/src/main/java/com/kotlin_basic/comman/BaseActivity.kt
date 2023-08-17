package com.skyautonet.dtglite.bbiapp.comman

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.idi.activity.schedule.ScheduleactivityViewmodel
import com.idi.activity.schedule.ScheduleactivityViewmodelprovider
import com.idi.comman.IDIViewModelProvider
import com.idi.fragment.survey.SurveyViewmodelprovider
import com.idi.utility.Idimain
import com.idi.utility.observeEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity<T : ViewDataBinding, T1: ToolbarViewModel>(
    @LayoutRes private val layoutResourceId: Int,
    private val cls: Class<T1>,
) : AppCompatActivity() {

    protected val viewModel: T1  by lazy {
        ViewModelProvider(this)[cls]
    }

    private var _viewDataBinding: T? = null
    protected val viewDataBinding get() = _viewDataBinding!!

    private val onStopDisposables = CompositeDisposable()
    private val onDestroyDisposable = CompositeDisposable()


    private var simpleName = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         Log.d("${this.localClassName} ","lifeCycle onCreate")
        _viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        viewDataBinding.lifecycleOwner = this
        viewModel.isTaskRoot.postValue(isTaskRoot.not() || supportFragmentManager.backStackEntryCount != 0)


        viewModel.onBackPressed.observeEvent(this) {
            if (isTaskRoot.not() || supportFragmentManager.backStackEntryCount != 0) onBackPressed()
        }
    }


    override fun onDestroy() {

        Log.d("$simpleName"," lifeCycle onDestroy")

        onStopDisposables.clear()
        onDestroyDisposable.clear()

        super.onDestroy()

        _viewDataBinding = null
    }

    override fun onStart() {
        super.onStart()
        Log.d("$simpleName ","lifeCycle onStart")
    }

    override fun onStop() {

        Log.d("$simpleName ","lifeCycle onStop")
        onStopDisposables.clear()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        Log.v("$simpleName","lifeCycle onResume")
    }

    override fun onPause() {
        Log.v("$simpleName","lifeCycle onPause")
        super.onPause()
    }

    private fun addDestroyDisposable(vararg disposables: Disposable) =
        onDestroyDisposable.addAll(*disposables)

    private fun addStopDisposable(vararg disposables: Disposable) =
        onStopDisposables.addAll(*disposables)

    fun Disposable.disposeDestroy(): Disposable {
        addDestroyDisposable(this)
        return this
    }

    fun Disposable.disposeStop(): Disposable {
        addStopDisposable(this)
        return this
    }

}