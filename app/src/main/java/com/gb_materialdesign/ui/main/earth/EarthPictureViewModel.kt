package com.gb_materialdesign.ui.main.earth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.earthPicture.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepository
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepositoryImpl
import com.test.application.core.utils.AppState
import com.gb_materialdesign.utils.CORRUPTED_DATA
import com.gb_materialdesign.utils.REQUEST_ERROR
import com.gb_materialdesign.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Response

class EarthPictureViewModel (
    private val liveData: MutableLiveData<com.test.application.core.utils.AppState> = MutableLiveData(),
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
                        com.test.application.core.utils.AppState.Error(Throwable(SERVER_ERROR))
                    } else {
                        com.test.application.core.utils.AppState.Error(Throwable(message))
                    }
                }
            )
        }

        override fun onFailure(call: Call<EarthPictureResponse>, t: Throwable) {
            liveData.postValue(com.test.application.core.utils.AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: EarthPictureResponse): com.test.application.core.utils.AppState {
        return if (serverResponse.isEmpty()) {
            com.test.application.core.utils.AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            com.test.application.core.utils.AppState.SuccessEarthPicture(serverResponse)
        }
    }

    fun getLiveData(date: String): MutableLiveData<com.test.application.core.utils.AppState> {
        sendServerRequest(date)
        return liveData
    }

    private fun sendServerRequest(date : String) {
        liveData.value = com.test.application.core.utils.AppState.Loading
        earthPictureRepository.getPicturesOfEarth(date, callback)
    }
}