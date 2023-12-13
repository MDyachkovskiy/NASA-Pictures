package com.gb_materialdesign.ui.main.asteroids

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb_materialdesign.model.asteroids.AsteroidsListResponse
import com.gb_materialdesign.model.pictureOfTheDay.RemoteSourceNasaAPI
import com.gb_materialdesign.repository.spaceFragment.SpaceFragmentRepository
import com.gb_materialdesign.repository.spaceFragment.SpaceFragmentRepositoryImpl
import com.test.application.core.utils.AppState
import com.gb_materialdesign.utils.CORRUPTED_DATA
import com.gb_materialdesign.utils.REQUEST_ERROR
import com.gb_materialdesign.utils.SERVER_ERROR
import retrofit2.Call
import retrofit2.Response

class AsteroidsListViewModel(
    private val liveData: MutableLiveData<com.test.application.core.utils.AppState> = MutableLiveData(),
    private val spaceFragmentRepository: SpaceFragmentRepository
    = SpaceFragmentRepositoryImpl(RemoteSourceNasaAPI())
) :ViewModel() {

    private val callback = object: retrofit2.Callback<AsteroidsListResponse>{
        override fun onResponse(
            call: Call<AsteroidsListResponse>,
            response: Response<AsteroidsListResponse>
        ) {
            val serverResponse: AsteroidsListResponse? = response.body()
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

        override fun onFailure(call: Call<AsteroidsListResponse>, t: Throwable) {
            liveData.postValue(com.test.application.core.utils.AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: AsteroidsListResponse): com.test.application.core.utils.AppState {
        return if (serverResponse == null) {
            com.test.application.core.utils.AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            com.test.application.core.utils.AppState.SuccessAsteroidList(serverResponse)
        }
    }

    fun getLiveData(date: String?): MutableLiveData<com.test.application.core.utils.AppState>{
        spaceFragmentRepository.getAsteroidsList(date, callback)
        return liveData
    }

    fun getAsteroidsList(date: String?) {
        liveData.value = com.test.application.core.utils.AppState.Loading
        spaceFragmentRepository.getAsteroidsList(date, callback)
    }
}