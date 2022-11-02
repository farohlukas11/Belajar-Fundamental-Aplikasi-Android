package com.dicoding.githubuserappsub1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserappsub1.FavoritUserRepository
import com.dicoding.githubuserappsub1.api.ApiConfig
import com.dicoding.githubuserappsub1.database.FavoritUser
import com.dicoding.githubuserappsub1.model.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    private val _user = MutableLiveData<UserDetailResponse>()
    val user: LiveData<UserDetailResponse> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoritUserRepository: FavoritUserRepository = FavoritUserRepository(application)

    init {
        findUserDetail("")
    }

    fun findUserDetail(detailUsername: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersDetail(detailUsername)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _user.value = responseBody!!
                } else {
                    Log.e(TAG, "onFailureRes: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun addFavoritUser(favoritUser: FavoritUser) {
        mFavoritUserRepository.addFavoritUser(favoritUser)
    }

    fun checkUser(id: Int): LiveData<Int> = mFavoritUserRepository.checkUser(id)

    fun deleteFavoritUser(id: Int) {
        mFavoritUserRepository.deleteFavoritUser(id)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}