package com.gb_materialdesign.ui.main.pictureOfTheDay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.PictureOfTheDayResponse
import com.gb_materialdesign.model.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.PictureOfTheDayRepository
import com.gb_materialdesign.repository.PictureOfTheDayRepositoryImpl
import com.gb_materialdesign.ui.main.appState.AppState
import retrofit2.Call
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

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
            AppState.Success(serverResponse)
        }
    }

    fun getLiveData(): MutableLiveData<AppState> {
        sendServerRequest()
        return liveData
    }

   fun getPictureOfTheDay() {
        pictureOfTheDayRepository.getPictureOfTheDay(callback)
    }

    fun getPictureOfTheDayByDate(date: String) {
        pictureOfTheDayRepository.getPictureOfTheDayByDate(date, callback)
    }

    private fun sendServerRequest() {
        liveData.value = AppState.Loading
        pictureOfTheDayRepository.getPictureOfTheDay(callback)
    }

}