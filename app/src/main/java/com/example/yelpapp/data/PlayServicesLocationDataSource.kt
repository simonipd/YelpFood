package com.example.yelpapp.data

import android.annotation.SuppressLint
import android.app.Application
import com.example.yelpapp.data.datasource.LocationDataSource
import com.example.yelpapp.domain.Coordinates

import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor(
    application: Application
) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Coordinates? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result?.let { Coordinates(it.latitude, it.longitude) })
                }
        }
}
