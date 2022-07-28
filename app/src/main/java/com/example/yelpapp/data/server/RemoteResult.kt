package com.example.yelpapp.data.server

import com.example.yelpapp.data.server.model.RemoteBusiness

data class RemoteResult(
    val businesses: List<RemoteBusiness>,
    val total: Int
)