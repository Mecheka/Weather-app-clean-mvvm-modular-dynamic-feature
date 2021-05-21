package com.example.home.utils

import android.text.format.DateFormat
import java.util.*

class DateTimeUtil {

    companion object {
        fun convertMillisecondToDate(dateTime: Int?): String {
            dateTime?.let {
                val date = Calendar.getInstance().apply { timeInMillis = checkMillisecond(it) }
                val format = DateFormat.format("EEE dd MMM HH mm", date)
                return format.toString()
            }

            return ""
        }

        private fun checkMillisecond(millisecond: Int): Long {
            val s = StringBuilder(millisecond.toString())
            while (s.length < 13) {
                s.append(0)
            }

            return s.toString().toLong()
        }
    }
}