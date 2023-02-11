package com.gb_materialdesign.repository

import com.gb_materialdesign.model.PictureOfTheDayResponse

interface PictureOfTheDayRepository {
    fun getPictureOfTheDay (callback: retrofit2.Callback<PictureOfTheDayResponse>)
}