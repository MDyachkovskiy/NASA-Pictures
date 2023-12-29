package com.test.application.core.domain.pictureOfTheDay

data class PictureOfTheDay(
    val copyright: String = "Unknown",
    val date: String = "Unknown",
    val explanation: String = "No explanation",
    val hdurl: String = "",
    val mediaType: String = "Unknown",
    val serviceVersion: String = "Unknown",
    val title: String = "Untitled",
    val url: String = ""
)