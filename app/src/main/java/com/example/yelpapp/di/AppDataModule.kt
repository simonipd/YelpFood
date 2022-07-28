package com.example.yelpapp.di

import com.example.yelpapp.data.AndroidPermissionChecker
import com.example.yelpapp.data.PermissionChecker
import com.example.yelpapp.data.PlayServicesLocationDataSource
import com.example.yelpapp.data.database.BusinessRoomDataSource
import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.data.datasource.LocationDataSource
import com.example.yelpapp.data.server.BusinessServerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: BusinessRoomDataSource): BusinessLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: BusinessServerDataSource): BusinessRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}