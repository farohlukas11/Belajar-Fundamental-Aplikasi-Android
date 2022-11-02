package com.dicoding.githubuserappsub1.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserappsub1.FavoritUserRepository
import com.dicoding.githubuserappsub1.database.FavoritUser

class FavoritViewModel(application: Application) : ViewModel() {

    private var _listUserFavorite =  MutableLiveData<List<FavoritUser>>()
    var listUserFavorite: LiveData<List<FavoritUser>> = _listUserFavorite

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val mediator = MediatorLiveData<Unit>()

    private val mFavoritUserRepository: FavoritUserRepository = FavoritUserRepository(application)

    private fun getAllFavoriteUser(): LiveData<List<FavoritUser>> = mFavoritUserRepository.getAllFavoritUser()

    init {
        getAllFavorite()
    }

    private fun getAllFavorite() {
        _isLoading.value = true
        mediator.addSource(getAllFavoriteUser()) {
            _listUserFavorite.value = it
            _isLoading.value = false
        }
    }

}