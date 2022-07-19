package com.example.favouriteplayertracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favouriteplayertracker.data.local.Teams.TeamDataDao
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAverages
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAvgDao

@Database(entities = [FavouritePlayer::class, TeamEntity::class, SeasonAverages::class],
    version = 1, exportSchema = true
//    ,
//    autoMigrations = [AutoMigration (from = 1, to = 2)]
)

abstract class LocalDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDataDao
    abstract fun userListDao(): UserListDao
    abstract fun seasonAvgDao(): SeasonAvgDao

    companion object {

        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, LocalDatabase::class.java, "user_list_database")
                    .build()
            }

            return INSTANCE!!
        }

    }

}