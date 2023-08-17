package com.idi.comman

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.gson.Gson
import com.idi.R
import com.idi.activity.splashscreen.SplashScreen
import com.idi.database.IDIRepository
import com.idi.fragment.response.LogoutResponse
import com.idi.utility.Idimain
import com.idi.utility.emitEvent
import com.idi.utility.observeEvent
import com.only.restapi.RestApi.ApiClass
import com.only.restapi.RestApi.DataresponseListener
import com.only.restapi.RestApi.Restclient
import com.only.restapi.Utility.Prefesmanager
import com.skyautonet.dtglite.bbiapp.comman.ToolbarViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseActivity2<T : ViewDataBinding>(
    @LayoutRes private val layoutResourceId: Int,
) : AppCompatActivity() {


    private var _viewDataBinding: T? = null
    protected val viewDataBinding get() = _viewDataBinding!!

    private val onStopDisposables = CompositeDisposable()
    private val onDestroyDisposable = CompositeDisposable()


    private var simpleName = this.javaClass.simpleName

    private lateinit var layProfile: LinearLayout
    private lateinit var imgProfile: ShapeableImageView

    private var dialoglogout:Dialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("${this.localClassName} ","lifeCycle onCreate")
        _viewDataBinding = DataBindingUtil.setContentView(this, layoutResourceId)
        viewDataBinding.lifecycleOwner = this

        layProfile=findViewById(R.id.layProfile)
        imgProfile=findViewById(R.id.imgProfile)

        if(Prefesmanager.containKey(Prefesmanager.Profile)!!)
        {
            val profileUrl= Restclient.BASE_URL+ Prefesmanager.readStringPrefVal(Prefesmanager.Profile)
            Glide.with(this).load(profileUrl).placeholder(R.drawable.ic_profile).into(imgProfile)
        }

        layProfile.setOnClickListener(View.OnClickListener {
            logout()
        })

       /* viewModel.isTaskRoot.postValue(isTaskRoot.not() || supportFragmentManager.backStackEntryCount != 0)
        viewModel.onBackPressed.observeEvent(this) {
            if (isTaskRoot.not() || supportFragmentManager.backStackEntryCount != 0) onBackPressed()
        }*/
    }

    private fun logout()
    {
        val dialog = Dialog(this)
        dialoglogout=dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.layout_logout)
        dialog.show()
        val window = dialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setGravity(Gravity.CENTER)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        val btnClose=dialog.findViewById<AppCompatButton>(R.id.btnClose)
        val btnLogout=dialog.findViewById<AppCompatButton>(R.id.btnLogout)
        val imgProfile=dialog.findViewById<ShapeableImageView>(R.id.imgProfile)
        val txtName=dialog.findViewById<AppCompatTextView>(R.id.txtName)
        val txtMobileNumber=dialog.findViewById<AppCompatTextView>(R.id.txtMobileNumber)
        val txtEmail=dialog.findViewById<AppCompatTextView>(R.id.txtEmail)

        if(Prefesmanager.containKey(Prefesmanager.Profile)!!)
        {
            Glide.with(this).load(Restclient.BASE_URL+Prefesmanager.readStringPrefVal(Prefesmanager.Profile)).placeholder(R.drawable.ic_profile).into(imgProfile)
        }
        if(Prefesmanager.containKey(Prefesmanager.Name)!!)
        {
            txtName.setText(Prefesmanager.readStringPrefVal(Prefesmanager.Name))
        }
        if(Prefesmanager.containKey(Prefesmanager.Mobile)!!)
        {
            txtMobileNumber.setText(Prefesmanager.readStringPrefVal(Prefesmanager.Mobile))
        }
        if(Prefesmanager.containKey(Prefesmanager.Email)!!)
        {
            txtEmail.setText(Prefesmanager.readStringPrefVal(Prefesmanager.Email))
        }

        btnClose.setOnClickListener {
            dialog.cancel()
        }

        btnLogout.setOnClickListener {
            logoutApi()
        }
    }

    private fun logoutApi(){

        if(Utils.isInternetAvailable())
        {
            Utils.showProgressDialog(this)
            ApiClass.logout(object : DataresponseListener {
                override fun onSuccessresponse(response: String) {
                    val logoutResponse: LogoutResponse = Gson().fromJson(response, LogoutResponse::class.java)
                    Utils.hideProgressDialog()
                    if(logoutResponse.success)
                    {
                        Utils.showMessage(this@BaseActivity2,logoutResponse.message)
                        clearData()
                    }else
                    {
                        Utils.showMessage(this@BaseActivity2,logoutResponse.message)
                    }
                }

                override fun onFailure() {
                    Utils.showMessage(this@BaseActivity2,getString(R.string.please_try_again_later))
                    Utils.hideProgressDialog()
                }
            })
        }else
        {
            Utils.showMessage(this@BaseActivity2,getString(R.string.no_internet))
        }
    }

    private fun clearData()
    {
        CoroutineScope(Dispatchers.IO).launch {
            (application as Idimain).repository.deleteTableResponse()
            (application as Idimain).repository.deleteTableSchedule()
            (application as Idimain).repository.deleteTableHabitation()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            Prefesmanager.removeAllKey()
            val intentMain= Intent(this, SplashScreen::class.java)
            startActivity(intentMain)
            finishAffinity()
        }, 100)
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