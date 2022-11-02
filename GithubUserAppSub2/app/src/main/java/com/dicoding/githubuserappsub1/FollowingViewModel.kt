package com.dicoding.githubuserappsub1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _listUserFollowing = MutableLiveData<List<UserResponseItem>>()
    val lisUserFollowing: LiveData<List<UserResponseItem>> = _listUserFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUserFollowing("")
    }

    fun findUserFollowing(detailUsername: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollowing(detailUsername)
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listUserFollowing.value = responseBody!!
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

    companion object {
        private const val TAG = "FollowingViewModel"
    }
}