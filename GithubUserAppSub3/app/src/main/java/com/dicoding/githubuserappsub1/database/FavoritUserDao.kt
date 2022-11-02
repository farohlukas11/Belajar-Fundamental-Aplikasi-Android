package com.dicoding.githubuserappsub1.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoritUser(favoritUser: FavoritUser)

    @Query("SELECT * from favorituser ORDER BY id ASC")
    fun getAllFavoritUser(): LiveData<List<FavoritUser>>

    @Query("SELECT count(*) from favorituser where favorituser.id = :id")
    fun checkUser(id: Int): LiveData<Int>

    @Query("DELETE from favorituser where favorituser.id = :id")
    fun deleteFavoritUser(id: Int)
}