package com.idi.activity.login

import Utils
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.idi.R
import com.idi.activity.addresponse.AddResponseViewModel
import com.idi.activity.main.LoginResponse
import com.idi.database.IDIRepository
import com.idi.utility.Event
import com.idi.utility.Idimain
import com.idi.utility.emitEvent
import com.idi.utility.toLiveData
import com.only.restapi.RestApi.ApiClass
import com.only.restapi.RestApi.DataresponseListener
import com.only.restapi.RestApi.Restclient
import com.only.restapi.Utility.Prefesmanager
import com.skyautonet.dtglite.bbiapp.comman.ToolbarViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat

data class LoginViewmodel(
    private val idiRepository: IDIRepository,
    private val _error: MutableLiveData<String> = MutableLiveData(),//error message
    val isShowDialog: MutableLiveData<Boolean> = MutableLiveData(),//show progres dialog
    val setMessage: MutableLiveData<Event<String>> = MutableLiveData(),//api response message
    val edtNumber: MutableLiveData<String> = MutableLiveData(),
    val resendText: MutableLiveData<String> = MutableLiveData(),
    val resendVisible: MutableLiveData<Boolean> = MutableLiveData(false),
    val edtOne: MutableLiveData<String> = MutableLiveData(),
    val edtTwo: MutableLiveData<String> = MutableLiveData(),
    val edtThree: MutableLiveData<String> = MutableLiveData(),
    val edtFour: MutableLiveData<String> = MutableLiveData(),
    val isOtpvisible: MutableLiveData<Boolean> = MutableLiveData(false),
    val isContinuevisible: MutableLiveData<Boolean> = MutableLiveData(false),
    val isVerifyvisible: MutableLiveData<Boolean> = MutableLiveData(false),
    val isSuccessfullvisible: MutableLiveData<Boolean> = MutableLiveData(false),
    val gotoNext: MutableLiveData<Boolean> = MutableLiveData(false),
) : ToolbarViewModel() {

    val error = _error.toLiveData()

    fun edtNumber(value:CharSequence)
    {
        edtNumber.postValue(value.toString())
        if(value.toString().length==10) {
            isContinuevisible.postValue(true)
        }
        else {
            isContinuevisible.postValue(false)
        }
    }

    fun edtOne(value:CharSequence) {
        edtOne.postValue(value.toString())
        verifyVisible(value.toString(),edtTwo.value.toString(),edtThree.value.toString(),edtFour.value.toString())
    }
    fun edtTwo(value:CharSequence) {
        edtTwo.postValue(value.toString())
        verifyVisible(edtOne.value.toString(),value.toString(),edtThree.value.toString(),edtFour.value.toString())
    }
    fun edtThree(value:CharSequence) {
        edtThree.postValue(value.toString())
        verifyVisible(edtOne.value.toString(),edtTwo.value.toString(),value.toString(),edtFour.value.toString())
    }
    fun edtFour(value:CharSequence) {
        edtFour.postValue(value.toString())
        verifyVisible(edtOne.value.toString(),edtTwo.value.toString(),edtThree.value.toString(),value.toString())
    }


    private fun verifyVisible(one:String,two:String,three:String,four:String)
    {
        if(one.length==1 && two.length==1 && three.length==1 && four.length==1)
        {
                isVerifyvisible.postValue(true)
        }else
        {
            isVerifyvisible.postValue(false)
        }
    }

    fun onClickEdit()
    {
        edtOne.value=""
        edtTwo.value=""
        edtThree.value=""
        edtFour.value=""
        isOtpvisible.postValue(false)
        isVerifyvisible.postValue(false)
    }

    fun onClickVerify()
    {
        if(Utils.isInternetAvailable())
        {
            val otp=edtOne.value+edtTwo.value+edtThree.value+edtFour.value
            isShowDialog.postValue(true)
            ApiClass.verifyMobileNumber(otp,object : DataresponseListener {
                override fun onSuccessresponse(response: String) {
                    val loginResponse:LoginResponse=Gson().fromJson(response,LoginResponse::class.java)
                    isShowDialog.postValue(false)
                    if(loginResponse.success)
                    {
                        Prefesmanager.savePrefsVal(Prefesmanager.Token,loginResponse.token)
                        if(!loginResponse.user_data.isNullOrEmpty())
                        {
                            clearData()
                            Prefesmanager.savePrefsVal(Prefesmanager.UserId,loginResponse.user_data.get(0).id)
                            Prefesmanager.savePrefsVal(Prefesmanager.Mobile,loginResponse.user_data.get(0).mobile)

                            if(loginResponse.user_data.get(0).email!=null) Prefesmanager.savePrefsVal(
                                Prefesmanager.Email,loginResponse.user_data.get(0).email)

                            if(loginResponse.user_data.get(0).name!=null) Prefesmanager.savePrefsVal(
                                Prefesmanager.Name,loginResponse.user_data.get(0).name)

                            if(loginResponse.user_data.get(0).avatar!=null){
                                Prefesmanager.savePrefsVal(Prefesmanager.Profile,loginResponse.user_data.get(0).avatar)
                            }

                            isSuccessfullvisible.postValue(true)
                            Handler(Looper.getMainLooper()).postDelayed({
                                gotoNext.postValue(true)
                            }, 1000)
                        }
                    }else
                    {
                        setMessage.emitEvent(loginResponse.message)
                    }
                }

                override fun onFailure() {
                    setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.please_try_again_later))
                    isShowDialog.postValue(false)
                }
            })
        }else
        {
            setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.no_internet))
        }

    }

    private fun clearData()
    {
        CoroutineScope(Dispatchers.IO).launch {
            idiRepository.deleteTableResponse()
            idiRepository.deleteTableSchedule()
            idiRepository.deleteTableHabitation()
        }
    }
    
    fun onClickContinue()
    {
        if(Utils.isInternetAvailable())
        {
            isShowDialog.postValue(true)
            ApiClass.sendOTP(edtNumber.value.toString(),object : DataresponseListener {
                override fun onSuccessresponse(response: String) {
                    val loginResponse: LoginResponse = Gson().fromJson(response, LoginResponse::class.java)
                    isShowDialog.postValue(false)
                    if(loginResponse.success)
                    {
                        isOtpvisible.postValue(true)
                        startCountDown()
                    }else
                    {
                        setMessage.emitEvent(loginResponse.message)
                    }
                }

                override fun onFailure() {
                    setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.please_try_again_later))
                    isShowDialog.postValue(false)
                }
            })
        }else
        {
            setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.no_internet))
        }

    }

    fun resendOTP()
    {
        if(Utils.isInternetAvailable())
        {
            isShowDialog.postValue(true)
            ApiClass.sendOTP(edtNumber.value.toString(),object : DataresponseListener {
                override fun onSuccessresponse(response: String) {
                    isShowDialog.postValue(false)
                    startCountDown()
                }

                override fun onFailure() {
                    setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.please_try_again_later))
                    isShowDialog.postValue(false)
                }
            })
        }else
        {
            setMessage.emitEvent(Idimain.getContext()!!.getString(R.string.no_internet))
        }

    }

    private fun startCountDown()
    {
        resendVisible.postValue(false)

        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                    val second=millisUntilFinished / 1000
                    if(second.toString().length==1)
                    {
                        val timing="00:0"+second
                        resendText.postValue(Idimain.getContext()!!.getString(R.string.otp_expire_in)+" "+ timing)
                    }else
                    {
                        val timing="00:"+second
                        resendText.postValue(Idimain.getContext()!!.getString(R.string.otp_expire_in)+" "+ timing)
                    }
            }

            override fun onFinish() {
                resendText.postValue(Idimain.getContext()!!.getString(R.string.resend))
                resendVisible.postValue(true)
                isVerifyvisible.postValue(false)
            }
        }
        timer.start()
    }
}

class LoginViewmodelprovider(private val repository : IDIRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(LoginViewmodel::class.java))
        {
            return LoginViewmodel(repository) as T
        }
        throw IllegalArgumentException("Viewmodel class not found..")
    }
}
