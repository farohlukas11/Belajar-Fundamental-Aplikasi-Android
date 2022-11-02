package com.dicoding.githubuserappsub1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritUser::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {

    abstract fun favoritUserDao(): FavoritUserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}