package com.example.yelpapp.di

import android.app.Application
import androidx.room.Room

import com.example.yelpapp.data.database.BusinessDataBase

import com.example.yelpapp.data.server.RemoteService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application) =
        Room.databaseBuilder(
            app,
            BusinessDataBase::class.java,
            "business-db"
        ).build()

    @Provides
    @Singleton
    fun provideBusinessDao(db: BusinessDataBase) =
        db.businessDao()


    @Provides
    @Singleton
    @TokenInterceptor
    fun provideTokenInterceptor(
        @Token token : String,
    ) = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer $token")
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        @TokenInterceptor tokenInterceptor: Interceptor
    ): OkHttpClient = HttpLoggingInterceptor().run {
        level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(this)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(
        @ApiUrl url: String,
        okHttpClient: OkHttpClient
    ) : RemoteService = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Provides
    @Singleton
    @ApiUrl
    fun provideUrl() = "https://api.yelp.com/v3/"

    @Provides
    @Singleton
    @Token
    fun provideToken() = "R1R9DOL09jglEGD1X_uu0nwvLAd0JhiLujEZqFfa9YwJjroOHFI_PsHiUFUwkDrlyMq4PHmOouElxOCMgaOlhEa2Cymwl4k2LU09ytvNMomlwbd0sQCeuINLcMQWYnYx"

}