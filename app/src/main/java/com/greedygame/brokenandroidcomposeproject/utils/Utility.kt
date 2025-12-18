package com.greedygame.brokenandroidcomposeproject.utils

import androidx.compose.foundation.layout.RowScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Utility {

    companion object {
        fun RowScope.formatDate(dateString: String): String {
            return try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")
                val date = inputFormat.parse(dateString)
                val outputFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
                outputFormat.format(date ?: Date())
            } catch (e: Exception) {
                "Recent"
            }
        }
    }
}