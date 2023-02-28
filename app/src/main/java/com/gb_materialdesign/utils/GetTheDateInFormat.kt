package com.gb_materialdesign.utils

import java.text.SimpleDateFormat
import java.util.*

fun getTheDateInFormat (decreaseDays: Int) : String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, -decreaseDays)
    val date = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(date)
}