package com.example.dailynews.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

// Utility function to format date string
fun formatDate(dateString: String): String {
    val instant = Instant.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
