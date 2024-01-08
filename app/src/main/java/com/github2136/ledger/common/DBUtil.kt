package com.github2136.ledger.common

import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.math.BigDecimal

/**
 * Created by YB on 2020/3/11
 * DataBindingUtil
 */
object DBUtil {

    @InverseMethod("str2int")
    @JvmStatic
    fun int2str(value: Int?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2int(value: String?): Int? {
        return value?.let {
            try {
                it.toInt()
            } catch (e: Exception) {
                null
            }
        }
    }

    @InverseMethod("str2double")
    @JvmStatic
    fun double2str(value: Double?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2double(value: String?): Double? {
        return value?.let {
            try {
                it.toDouble()
            } catch (e: Exception) {
                null
            }
        }
    }

    @InverseMethod("str2float")
    @JvmStatic
    fun float2str(value: Float?): String? {
        return value?.let { BigDecimal(it.toString()).toPlainString() }
    }

    @JvmStatic
    fun str2float(value: String?): Float? {
        return value?.let {
            try {
                it.toFloat()
            } catch (e: Exception) {
                null
            }
        }
    }

    /**
     * TextView
     */
    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Byte?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Short?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Int?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Long?) {
        view.text = value?.toString()
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Float?) {
        view.text = value?.let {
            BigDecimal(it.toString()).toPlainString()
        }
    }

    @BindingAdapter("android:text")
    @JvmStatic
    fun setText(view: TextView, value: Double?) {
        view.text = value?.let {
            BigDecimal(it.toString()).toPlainString()
        }
    }

    @JvmStatic
    @BindingAdapter("android:adapter")
    fun <T> autoCompleteAdapter(autoComplete: AutoCompleteTextView, listAdapter: T) where T : ListAdapter, T : Filterable {
        autoComplete.setAdapter(listAdapter)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Calendar
    ///////////////////////////////////////////////////////////////////////////
    @BindingAdapter(
        value = ["android:calendar_select", "android:calendar_outrange"],
        requireAll = false
    )
    @JvmStatic
    fun calendarViewOnCalendarSelectListener(
        calendar: CalendarView,
        calendarViewSelect: CalendarViewSelect?,
        calendarOutRange: CalendarOutRange?
    ) {
        if (calendarViewSelect != null || calendarOutRange != null) {

            calendar.setOnCalendarSelectListener(object : CalendarView.OnCalendarSelectListener {
                override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                    calendarViewSelect?.onCalendarSelect(calendar, isClick)
                }

                override fun onCalendarOutOfRange(calendar: Calendar?) {
                    calendarOutRange?.onCalendarOutOfRange(calendar)
                }
            })
        }
    }

    interface CalendarViewSelect {
        fun onCalendarSelect(calendar: Calendar?, isClick: Boolean)
    }

    interface CalendarOutRange {
        fun onCalendarOutOfRange(calendar: Calendar?)
    }
}