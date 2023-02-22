package com.gb_materialdesign.ui.main.earth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepository
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepositoryImpl
import com.gb_materialdesign.ui.main.appState.AppState
import retrofit2.Call
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class EarthPictureViewModel (
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val earthPictureRepository: EarthPictureRepository =
        EarthPictureRepositoryImpl(RemoteSourceNasaAPI())
): ViewModel() {

    private val callback = object: retrofit2.Callback<EarthPictureResponse> {

        override fun onResponse(
            call: Call<EarthPictureResponse>,
            response: Response<EarthPictureResponse>
        ) {
            val serverResponse: EarthPictureResponse? = response.body()
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

        override fun onFailure(call: Call<EarthPictureResponse>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse(serverResponse: EarthPictureResponse): AppState {
        return if (serverResponse.isNullOrEmpty()) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.SuccessEarthPicture(serverResponse)
        }
    }

    fun getLiveData(date: String): MutableLiveData<AppState> {
        sendServerRequest(date)
        return liveData
    }

    private fun sendServerRequest(date : String) {
        liveData.value = AppState.Loading
        earthPictureRepository.getPicturesOfEarth(date, callback)
    }

}