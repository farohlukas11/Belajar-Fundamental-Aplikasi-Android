package com.dicoding.githubuserappsub1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dicoding.githubuserappsub1.FavoritUserRepository
import com.dicoding.githubuserappsub1.model.UserResponse
import com.dicoding.githubuserappsub1.model.UserResponseItem
import com.dicoding.githubuserappsub1.api.ApiConfig
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _listUser = MutableLiveData<List<UserResponseItem>>()
    val listUser: LiveData<List<UserResponseItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isTheme = MutableLiveData<Boolean>()
    val isTheme: LiveData<Boolean> = _isTheme

    val mediator = MediatorLiveData<Unit>()

    private val mFavoritUserRepository: FavoritUserRepository = FavoritUserRepository(application)

    init {
        getThemeSettings()
    }

    fun findUserSearch(detailUsername: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersSearch(detailUsername)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listUser.value = responseBody?.items
                } else {
                    Log.e(TAG, "onFailureRes: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listUser.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailureRes: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getThemeSettings() {
        val theme = mFavoritUserRepository.getThemeSetting().asLiveData(Dispatchers.IO)
        mediator.addSource(theme) {
            _isTheme.value = it
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}