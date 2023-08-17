package com.idi.comman

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.content.ContextCompat
import com.idi.R
import java.util.*

class BetterSpinner : AppCompatAutoCompleteTextView, AdapterView.OnItemClickListener {

    private val MAX_CLICK_DURATION = 200
    private var startClickTime: Long = 0
    private var isPopup = false
    private var mPosition = ListView.INVALID_POSITION

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

    protected override fun onFocusChanged(
        focused: Boolean, direction: Int,
        previouslyFocusedRect: Rect?
    ) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            if (getAdapter() != null) performFiltering("", 0)
            val imm =
                getContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getWindowToken(), 0)
            setKeyListener(null)
            dismissDropDown()
        } else {
            isPopup = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled()) return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = Calendar.getInstance().timeInMillis
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = Calendar.getInstance().timeInMillis - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) {
                    isPopup = if (isPopup && isPopupShowing()) {
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


    fun getPosition(): Int {
        return mPosition
    }
}