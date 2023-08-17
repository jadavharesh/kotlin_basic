package com.skyautonet.dtglite.bbiapp.comman

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class ContentFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int
) : Fragment() {

    private var _viewDataBinding: T? = null
    protected val viewDataBinding get() = _viewDataBinding!!
    private var simpleName = this.javaClass.simpleName

    private val onStopDisposables = CompositeDisposable()
    private val onDestroyDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _viewDataBinding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        viewDataBinding.lifecycleOwner = viewLifecycleOwner

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("$simpleName","lifeCycle onViewCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.d("$simpleName","lifeCycle onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("$simpleName","lifeCycle onStop")
        onStopDisposables.clear()
    }

    override fun onDestroyView() {

        Log.i("$simpleName","lifeCycle onDestroy")
        super.onDestroyView()
        _viewDataBinding = null
    }

    fun Disposable.disposeOnStop() {
        onStopDisposables.add(this)
    }

    fun Disposable.disposeOnDestroy() {
        onDestroyDisposable.add(this)
    }

}