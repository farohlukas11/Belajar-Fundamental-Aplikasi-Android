package com.dicoding.githubuserappsub1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserappsub1.model.UserResponseItem
import com.dicoding.githubuserappsub1.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    private val _listUserFollower = MutableLiveData<List<UserResponseItem>>()
    val listUserFollower: LiveData<List<UserResponseItem>> = _listUserFollower

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUserFollower("")
    }

    fun findUserFollower(detailUsername: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsersFollower(detailUsername)
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _listUserFollower.value = responseBody!!
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
        private const val TAG = "FollowerViewModel"
    }
}