package com.martiniriga.moviedb.utils

import android.content.Context
import android.util.TypedValue
import java.text.SimpleDateFormat
import java.util.*


object Helpers {

    fun formatLongEndDay(dateStr: String): String {
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(dateStr)
        return SimpleDateFormat("dd MMMM YYYY", Locale.getDefault()).format(date)
    }


    fun dpToPx(c: Context, dp: Int): Int {
        val r = c.resources
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp.toFloat(),
                r.displayMetrics
            )
        )
    }
}
