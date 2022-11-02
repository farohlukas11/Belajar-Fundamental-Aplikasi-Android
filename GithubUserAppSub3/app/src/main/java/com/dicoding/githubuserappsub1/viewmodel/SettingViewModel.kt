package com.dicoding.githubuserappsub1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.dicoding.githubuserappsub1.FavoritUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val mFavoritUserRepository: FavoritUserRepository = FavoritUserRepository(application)

    fun getThemeSettings(): LiveData<Boolean> =
        mFavoritUserRepository.getThemeSetting().asLiveData(Dispatchers.IO)

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            mFavoritUserRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}