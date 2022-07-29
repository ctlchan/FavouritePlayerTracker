package com.example.favouriteplayertracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.favouriteplayertracker.data.local.Teams.TeamDataDao
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.local.newsData.ArticleConverter
import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import com.example.favouriteplayertracker.data.local.newsData.NewsDao
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAverages
import com.example.favouriteplayertracker.data.local.seasonAverages.SeasonAvgDao
import com.example.favouriteplayertracker.data.local.twitterData.TweetConverter
import com.example.favouriteplayertracker.data.local.twitterData.TweetDao
import com.example.favouriteplayertracker.data.local.twitterData.Tweets

@Database(entities = [FavouritePlayer::class, TeamEntity::class, SeasonAverages::class,
                     PlayerNews::class, Tweets::class],
    version = 1, exportSchema = true
//    ,
//    autoMigrations = [AutoMigration (from = 1, to = 2)]
)
@TypeConverters(ArticleConverter::class, TweetConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDataDao
    abstract fun userListDao(): UserListDao
    abstract fun seasonAvgDao(): SeasonAvgDao
    abstract fun newsDao(): NewsDao
    abstract fun tweetsDao(): TweetDao

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