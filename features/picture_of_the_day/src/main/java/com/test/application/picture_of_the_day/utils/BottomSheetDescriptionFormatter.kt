package com.test.application.picture_of_the_day.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.LineHeightSpan
import android.text.style.QuoteSpan
import android.text.style.RelativeSizeSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import com.test.application.picture_of_the_day.R

class BottomSheetDescriptionFormatter(
    private var context: Context?
) {

    private val flag = 0
    fun formatDescription(description: String): SpannableString {

        var spannableDescription = SpannableString(prepareText(description))
        spannableDescription = applyBaseTextStyle(spannableDescription)
        spannableDescription = formatInitialSegment(spannableDescription)

        val highlightColor = ContextCompat.getColor(context!!, R.color.text_hightlight_color)
        spannableDescription = highlightKeyword(spannableDescription, "star", highlightColor)
        spannableDescription = highlightKeyword(spannableDescription, "stars", highlightColor)

        val indexOfLastCharOnFirstLine = 40
        if(description.length > indexOfLastCharOnFirstLine) {
            spannableDescription = addQuoteSpan(spannableDescription, 0, indexOfLastCharOnFirstLine)
        }

        return spannableDescription
    }

    private fun prepareText(description: String): String {
        val indexOfLastCharOnFirstLine = 40
        return if (description.length > indexOfLastCharOnFirstLine) {
            description.substring(0, indexOfLastCharOnFirstLine) + "\n" +
                    description.substring(indexOfLastCharOnFirstLine)
        } else {
            description
        }
    }

    private fun applyBaseTextStyle(spannable: SpannableString): SpannableString {
        val lineHeightInPx = 100
        spannable.setSpan(LineHeightSpan.Standard(lineHeightInPx), 0,
            spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    private fun formatInitialSegment(spannable: SpannableString): SpannableString {
        val fontSizeMultiplier = 2.0f
        val startIndex = 0
        spannable.setSpan(
            RelativeSizeSpan(fontSizeMultiplier),
            startIndex, startIndex + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    private fun highlightKeyword(
        spannable: SpannableString, keyword: String, color: Int
    ): SpannableString {
        val indexes = spannable.toString().indexesOf(keyword)
        indexes.forEach { index ->
            spannable.setSpan(ForegroundColorSpan(color), index, index+keyword.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            spannable.setSpan(UnderlineSpan(), index, index+keyword.length, flag)

        }
        return spannable
    }

    private fun String.indexesOf(substr: String, ignoreCase: Boolean = true): List<Int> =
        (if (ignoreCase) Regex(substr, RegexOption.IGNORE_CASE) else Regex(substr))
            .findAll(this).map { it.range.first }.toList()

    private fun addQuoteSpan(
        spannable: SpannableString, startIndex: Int, endIndex: Int
    ): SpannableString {
        val color = context!!.getColor(R.color.text_quote_color)
        val stripeWidthInPx = 20
        val gapWidthInPx = 100
        spannable.setSpan(
            QuoteSpan(color, stripeWidthInPx, gapWidthInPx),
            startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun cleanup() {
        context = null
    }
}