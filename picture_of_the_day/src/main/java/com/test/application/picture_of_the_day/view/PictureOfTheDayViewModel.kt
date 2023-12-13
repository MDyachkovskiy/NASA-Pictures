package com.test.application.picture_of_the_day.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.pictureOfTheDay.PictureOfTheDayResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.pictureOfTheDay.PictureOfTheDayRepository
import com.gb_materialdesign.repository.pictureOfTheDay.PictureOfTheDayRepositoryImpl
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.utils.CORRUPTED_DATA
import com.gb_materialdesign.utils.REQUEST_ERROR
import com.gb_materialdesign.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val pictureOfTheDayRepository: PictureOfTheDayRepository
    = PictureOfTheDayRepositoryImpl(RemoteSourceNasaAPI())
) : ViewModel() {

    private val callback = object : retrofit2.Callback<PictureOfTheDayResponse> {

        override fun onResponse(
            call: Call<PictureOfTheDayResponse>,
            response: Response<PictureOfTheDayResponse>
        ) {
            val serverResponse: PictureOfTheDayResponse? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    val message = response.message()
                    if (message.isNullOrEmpty()) {
                        AppState.Error(Throwable(SERVER_ERROR))
                    } else {
                        AppState.Error(Throwable(message))
                    }
                }
            )
        }

        override fun onFailure(call: Call<PictureOfTheDayResponse>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: PictureOfTheDayResponse): AppState {
        return if (serverResponse == null) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.SuccessTelescope(serverResponse)
        }
    }

    fun getLiveData(): MutableLiveData<AppState> {
        sendServerRequest()
        return liveData
    }

   fun getPictureOfTheDay() {
       liveData.value = AppState.Loading
       pictureOfTheDayRepository.getPictureOfTheDay(callback)
    }

    fun getPictureOfTheDayByDate(date: String) {
        liveData.value = AppState.Loading
        pictureOfTheDayRepository.getPictureOfTheDayByDate(date, callback)
    }

    private fun sendServerRequest() {
        liveData.value = AppState.Loading
        pictureOfTheDayRepository.getPictureOfTheDay(callback)
    }
}