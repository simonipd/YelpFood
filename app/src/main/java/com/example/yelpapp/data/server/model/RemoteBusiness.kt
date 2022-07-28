package com.example.yelpapp.data.server.model

import com.example.yelpapp.domain.Business as DomainModel

data class RemoteBusiness(
    val alias: String,
    val coordinates: Coordinates?,
    val display_phone: String,
    val distance: Double,
    val id: String,
    val image_url: String,
    val is_closed: Boolean,
    val location: Location?,
    val name: String,
    val phone: String,
    val price: String?,
    val rating: Double?,
    val review_count: Int,
    val url: String
)

fun RemoteBusiness.toDomainModel() = DomainModel(
    alias,
    display_phone,
    distance,
    id,
    image_url,
    is_closed,
    location?.address1 ?: location?.address2 ?: location?.address3 ?: "",
    location?.city,
    location?.country,
    name,
    phone,
    price,
    rating ?: 0.0,
    review_count,
    url,
    false
)