package com.gb_materialdesign.repository.pictureOfTheDay

import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay (callback: retrofit2.Callback<PictureOfTheDayResponse>)

    fun getPictureOfTheDayByDate (date:String, callback: retrofit2.Callback<PictureOfTheDayResponse>)
}