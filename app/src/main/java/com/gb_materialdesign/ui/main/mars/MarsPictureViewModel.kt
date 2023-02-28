package com.gb_materialdesign.ui.main.mars

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.marsPicture.MarsPictureResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.marsPicture.MarsPictureRepository
import com.gb_materialdesign.repository.marsPicture.MarsPictureRepositoryImpl
import com.gb_materialdesign.ui.main.appState.AppState
import com.gb_materialdesign.utils.CORRUPTED_DATA
import com.gb_materialdesign.utils.REQUEST_ERROR
import com.gb_materialdesign.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Response

class MarsPictureViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val marsPictureRepository: MarsPictureRepository =
        MarsPictureRepositoryImpl(RemoteSourceNasaAPI())
) : ViewModel() {

    private val callback = object : retrofit2.Callback<MarsPictureResponse> {
        override fun onResponse(
            call: Call<MarsPictureResponse>,
            response: Response<MarsPictureResponse>
        ) {
            val serverResponse: MarsPictureResponse? = response.body()
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

        override fun onFailure(call: Call<MarsPictureResponse>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

    }

    private fun checkResponse (serverResponse: MarsPictureResponse) : AppState {
       return if  (serverResponse == null) {
           AppState.Error (Throwable(CORRUPTED_DATA))
       } else {
           AppState.SuccessMarsPicture(serverResponse)
       }
    }

    fun getLiveData() : MutableLiveData<AppState> = liveData

    fun getPicturesOfMarsByCamera (camera: String?, date: String) {
        marsPictureRepository.getPictureOfMars(date, camera, callback)
    }

}