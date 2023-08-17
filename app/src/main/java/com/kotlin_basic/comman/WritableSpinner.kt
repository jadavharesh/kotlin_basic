package com.idi.comman

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.idi.R
import java.util.*

class WritableSpinner: AppCompatAutoCompleteTextView, OnItemClickListener {


    private val MAX_CLICK_DURATION = 200
    private var startClickTime: Long = 0
    private var isPopup = false
    private var mPosition = ListView.INVALID_POSITION
    private var mSelectionFromPopUp = false
    private var isWritable = true
    private var myText: CharSequence = ""



    constructor(context: Context?):super(context!!){
        onItemClickListener = this
    }

    constructor(context: Context?, arg1: AttributeSet?):super(context!!, arg1){
        onItemClickListener = this
    }

    constructor(context: Context?, arg1: AttributeSet?, arg2: Int):super(context!!, arg1, arg2) {
        onItemClickListener = this
    }


    override fun enoughToFilter(): Boolean {
        return true
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?)
    {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
            dismissDropDown()
        } else {
            isPopup = false
            if (!mSelectionFromPopUp) setText("")
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis
            }
            MotionEvent.ACTION_UP -> {
                inputType = if (isWritable) {
                    InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
                } else {
                    return true
                }
                isEnabled = true
                //for set text
                //setText("")
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) {
                    isPopup = if (isPopup) {
                        dismissDropDown()
                        false
                    } else {
                        requestFocus()
                        showDropDown()
                        true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }


    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        mPosition = position
        isPopup = false
    }

    fun setSelectionFromPopUp(mSelectionFromPopUp: Boolean) {
        this.mSelectionFromPopUp = mSelectionFromPopUp
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        var right = right
        val dropdownIcon: Drawable?
        dropdownIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_spinner)
        if (dropdownIcon != null) {
            right = dropdownIcon
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun replaceText(text: CharSequence?) {
        super.replaceText(text)
        mSelectionFromPopUp = true
        isEnabled = false
    }

    fun setPopup() {
        isPopup = false
    }

    fun getPosition(): Int {
        return mPosition
    }

    fun setWritable(writable: Boolean) {
        isWritable = writable
    }

    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
        mSelectionFromPopUp = false
    }

}