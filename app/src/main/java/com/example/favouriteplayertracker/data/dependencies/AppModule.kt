package com.example.favouriteplayertracker.data.dependencies

import android.content.Context
import com.example.favouriteplayertracker.data.local.LocalDatabase
import com.example.favouriteplayertracker.data.remote.nbaApi.NbaApi
import com.example.favouriteplayertracker.data.remote.newsApi.NewsApi
import com.example.favouriteplayertracker.data.repository.playerNews.PlayerNewsRepository
import com.example.favouriteplayertracker.data.repository.playerNews.PlayerNewsRepositoryImpl
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import com.example.favouriteplayertracker.data.repository.userList.UserListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Named("userListRepo")
    @Provides
    fun provideUserListRepository(
        @Named("database") db: LocalDatabase, @Named("NbaApi") nbaApi: NbaApi
    ): UserListRepository {
        // Dagger-Hilt knows that db is provided so it will do that for you.
        return UserListRepositoryImpl(db.userListDao(), nbaApi, db.teamDao(),
            db.seasonAvgDao())

    }


    @Singleton
    @Named("newsRepo")
    @Provides
    fun provideNewsRepository(
        @Named("database") db: LocalDatabase, @Named("NewsApi") newsApi: NewsApi
    ): PlayerNewsRepository {
        return PlayerNewsRepositoryImpl(db.userListDao(), db.newsDao(), newsApi)
    }


    @Singleton
    @Named("database")
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LocalDatabase = LocalDatabase.getDatabase(context)


    @Singleton
    @Named("NbaApi")
    @Provides
    fun provideNbaApi(): NbaApi {
        return Retrofit.Builder()
            .baseUrl("https://www.balldontlie.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NbaApi::class.java)
    }

    @Singleton
    @Named("NewsApi")
    @Provides
    fun provideNewsApi(): NewsApi {
        return Retrofit.Builder()
            .baseUrl("https://free-news.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

}