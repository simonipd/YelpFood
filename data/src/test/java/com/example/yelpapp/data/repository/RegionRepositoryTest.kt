package com.example.yelpapp.data.repository

import com.example.yelpapp.data.PermissionChecker
import com.example.yelpapp.data.PermissionChecker.Permission.*
import com.example.yelpapp.data.datasource.LocationDataSource
import com.example.yelpapp.domain.Coordinates
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest{

    @Test
    fun `Returns default coordinates when coarse permission not granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
        )

        val location = regionRepository.findLastLocation()

        assertEquals(RegionRepository.DEFAULT_REGION, location)
    }

    @Test
    fun `Returns region from location data source when permission granted`(): Unit = runBlocking {
        val expCoordinates = Coordinates(13.35656,45.5665)

        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastLocation() } doReturn expCoordinates },
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
        )

        val coordinates = regionRepository.findLastLocation()

        assertEquals(expCoordinates, coordinates)
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)