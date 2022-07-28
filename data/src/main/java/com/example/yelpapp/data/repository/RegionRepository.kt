package com.example.yelpapp.data.repository


import com.example.yelpapp.data.PermissionChecker
import com.example.yelpapp.data.datasource.LocationDataSource
import com.example.yelpapp.domain.Coordinates


import javax.inject.Inject

class RegionRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker : PermissionChecker
) {

    companion object {
        val DEFAULT_REGION = Coordinates(37.278989641,-121.862900146)
    }

    suspend fun findLastLocation(): Coordinates {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
             locationDataSource.findLastLocation() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}