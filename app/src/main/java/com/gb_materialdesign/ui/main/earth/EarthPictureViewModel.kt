package com.gb_materialdesign.ui.main.earth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepository
import com.gb_materialdesign.repository.earthPicture.EarthPictureRepositoryImpl
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

    private val callback = object: retrofit2.Callback<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse> {

        override fun onResponse(
            call: Call<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>,
            response: Response<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>
        ) {
            val serverResponse: com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse? = response.body()
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

        override fun onFailure(call: Call<com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse>, t: Throwable) {
            liveData.postValue(com.test.application.core.utils.AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: com.test.application.remote_data.dto.earthPictureResponse.EarthPictureResponse): com.test.application.core.utils.AppState {
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