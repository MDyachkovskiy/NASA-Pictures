package com.gb_materialdesign.repository.pictureOfTheDay

import com.test.application.core.domain.PictureOfTheDayResponse

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay (callback: retrofit2.Callback<com.test.application.core.domain.PictureOfTheDayResponse>)

    fun getPictureOfTheDayByDate (date:String, callback: retrofit2.Callback<com.test.application.core.domain.PictureOfTheDayResponse>)
}