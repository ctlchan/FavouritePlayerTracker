package com.example.favouriteplayertracker.data.dependencies

import android.content.Context
import com.example.favouriteplayertracker.data.local.LocalDatabase
import com.example.favouriteplayertracker.data.remote.NbaApi
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import com.example.favouriteplayertracker.data.repository.userList.UserListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Named("userListRepo")
    @Provides
    fun provideUserListRepository(
        @Named("database") db: LocalDatabase, @Named("NbaApi") nbaApi: NbaApi): UserListRepository {
        // Dagger-Hilt knows that db is provided so it will do that for you.
        return UserListRepositoryImpl(db.userListDao(), nbaApi)

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

}