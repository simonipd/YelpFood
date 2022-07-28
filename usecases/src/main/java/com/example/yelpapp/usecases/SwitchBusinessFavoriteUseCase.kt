package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import com.example.yelpapp.domain.Business
import javax.inject.Inject

class SwitchBusinessFavoriteUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
) {

    suspend operator fun invoke(business: Business) = businessRepository.switchFavorite(business)
}