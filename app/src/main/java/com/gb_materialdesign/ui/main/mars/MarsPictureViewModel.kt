package com.gb_materialdesign.ui.main.mars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.marsPicture.MarsPictureRepository
import com.gb_materialdesign.repository.marsPicture.MarsPictureRepositoryImpl
import com.gb_materialdesign.utils.CORRUPTED_DATA
import com.gb_materialdesign.utils.REQUEST_ERROR
import com.gb_materialdesign.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Response

class MarsPictureViewModel(
    private val liveData: MutableLiveData<com.test.application.core.utils.AppState> = MutableLiveData(),
    private val marsPictureRepository: MarsPictureRepository =
        MarsPictureRepositoryImpl(RemoteSourceNasaAPI())
) : ViewModel() {

    private val callback = object : retrofit2.Callback<com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse> {
        override fun onResponse(
            call: Call<com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse>,
            response: Response<com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse>
        ) {
            val serverResponse: com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse? = response.body()
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

        override fun onFailure(call: Call<com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse>, t: Throwable) {
            liveData.postValue(com.test.application.core.utils.AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse (serverResponse: com.test.application.remote_data.dto.marsPictureResponse.MarsPictureResponse) : com.test.application.core.utils.AppState {
       return if  (serverResponse == null) {
           com.test.application.core.utils.AppState.Error (Throwable(CORRUPTED_DATA))
       } else {
           com.test.application.core.utils.AppState.SuccessMarsPicture(serverResponse)
       }
    }

    fun getLiveData() : MutableLiveData<com.test.application.core.utils.AppState> = liveData

    fun getPicturesOfMarsByCamera (camera: String?, date: String) {
        liveData.value = com.test.application.core.utils.AppState.Loading
        marsPictureRepository.getPictureOfMars(date, camera, callback)
    }

}