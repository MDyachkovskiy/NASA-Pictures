package com.test.application.picture_of_the_day.utils

import android.content.Context
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.text.style.ScaleXSpan
import androidx.core.content.ContextCompat
import com.test.application.picture_of_the_day.R

class BottomSheetHeaderFormatter(
    private var context: Context?
) {
    private val startIndex = 0
    private val flag = 0

    fun formatHeader(header: String): SpannableString {
        val spannableHeader = SpannableString(header)
        setBackgroundForHeader(spannableHeader)
        scaleHeaderText(spannableHeader)
        return spannableHeader
    }

    private fun scaleHeaderText(spannable: SpannableString): SpannableString {
        val proportion = 1.5f
        spannable.setSpan(ScaleXSpan(proportion), 0, spannable.length, flag)
        return spannable
    }

    private fun setBackgroundForHeader(spannable: SpannableString): SpannableString {
        val color = context?.let { ContextCompat.getColor(it, R.color.header_background_color) }
        spannable.setSpan(color?.let { BackgroundColorSpan(it) }, startIndex, spannable.length, flag)
        return spannable
    }

    fun cleanup() {
        context = null
    }
}