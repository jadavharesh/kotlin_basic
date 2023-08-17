package com.idi.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import com.idi.R
import com.idi.activity.addresponse.AddResponseViewModel
import com.idi.activity.addresponse.AddResponseViewmodelprovider
import com.idi.activity.main.AdapterViewPager
import com.idi.activity.main.MainActivity
import com.idi.activity.main.ViewmodelMain
import com.idi.comman.BaseActivity2
import com.idi.databinding.ActivityLoginBinding
import com.idi.databinding.ActivityMainBinding
import com.idi.utility.Idimain
import com.idi.utility.observeEvent
import com.skyautonet.dtglite.bbiapp.Utils.AppConstants
import com.skyautonet.dtglite.bbiapp.comman.BaseActivity

class Login : BaseActivity2<ActivityLoginBinding>(
    layoutResourceId = R.layout.activity_login
) {

    private val viewModel : LoginViewmodel by viewModels {
        LoginViewmodelprovider((application as Idimain).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
    }

    private fun initView()
    {
        viewDataBinding.run {
            vm = viewModel
            activity = this@Login
        }

        viewModel.setMessage.observeEvent(this)
        {
            Utils.showMessage(this,it)
        }

        viewModel.isShowDialog.observe(this)
        {
            if(it) Utils.showProgressDialog(this) else Utils.hideProgressDialog()
        }

        viewModel.gotoNext.observe(this)
        {
            if(it)
            {
                val mainIntent=Intent(this,MainActivity::class.java)
                mainIntent.putExtra(AppConstants.isFromLogin,true)
                startActivity(mainIntent)
                finishAffinity()
            }
        }

        viewDataBinding.edtOne.doOnTextChanged { text, start, before, count ->
                if(text.toString().isNotBlank()) viewDataBinding.edtTwo.requestFocus()
        }

        viewDataBinding.edtTwo.doOnTextChanged { text, start, before, count ->
                if(text.toString().isNotBlank()) viewDataBinding.edtThree.requestFocus() else viewDataBinding.edtOne.requestFocus()
        }

        viewDataBinding.edtThree.doOnTextChanged { text, start, before, count ->
                if(text.toString().isNotBlank()) viewDataBinding.edtFour.requestFocus() else viewDataBinding.edtTwo.requestFocus()
        }
        viewDataBinding.edtFour.doOnTextChanged { text, start, before, count ->
            if(text.toString().isNullOrEmpty())  viewDataBinding.edtThree.requestFocus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}