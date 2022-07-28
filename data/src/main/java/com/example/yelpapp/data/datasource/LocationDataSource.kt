package com.example.yelpapp.data.datasource

import com.example.yelpapp.domain.Coordinates


interface LocationDataSource {
    suspend fun findLastLocation(): Coordinates?
}

