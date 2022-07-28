package com.example.yelpapp.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.yelpapp.domain.Business as DomainModel

@Entity
data class Business(
    @PrimaryKey
    val id: String,
    val alias: String,
    val display_phone: String,
    val distance: Double,
    val image_url: String,
    val is_closed: Boolean,
    val address : String,
    val city : String?,
    val country : String?,
    val name: String,
    val phone: String,
    val price: String?,
    val rating: Double,
    val review_count: Int,
    val url: String,
    val favorite : Boolean
)

fun Business.toDomainModel() = DomainModel(
    alias,
    display_phone,
    distance,
    id,
    image_url,
    is_closed,
    address,
    city,
    country,
    name,
    phone,
    price,
    rating,
    review_count,
    url,
    favorite
)

fun DomainModel.fromDomainModel() = Business(
    id,
    alias,
    display_phone,
    distance,
    image_url,
    is_closed,
    address,
    city,
    country,
    name,
    phone,
    price,
    rating,
    review_count,
    url,
    favorite
)