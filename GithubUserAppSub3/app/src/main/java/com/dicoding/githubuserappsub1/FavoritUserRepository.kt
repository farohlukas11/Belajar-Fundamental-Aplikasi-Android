package com.dicoding.githubuserappsub1

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuserappsub1.database.FavoritUser
import com.dicoding.githubuserappsub1.database.FavoritUserDao
import com.dicoding.githubuserappsub1.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoritUserRepository(application: Application) {
    private val mFavoritUserDao: FavoritUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val mDataPreferences: SettingPreferences

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mFavoritUserDao = db.favoritUserDao()
        mDataPreferences = SettingPreferences.getInstance(application)
    }

    fun getAllFavoritUser(): LiveData<List<FavoritUser>> = mFavoritUserDao.getAllFavoritUser()

    fun addFavoritUser(favoritUser: FavoritUser) {
        executorService.execute {
            mFavoritUserDao.addFavoritUser(favoritUser)
        }
    }

    fun checkUser(id: Int): LiveData<Int> = mFavoritUserDao.checkUser(id)

    fun deleteFavoritUser(id: Int) {
        executorService.execute {
            mFavoritUserDao.deleteFavoritUser(id)
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) = mDataPreferences.saveThemeSetting(isDarkModeActive)

    fun getThemeSetting() = mDataPreferences.getThemeSetting()
}