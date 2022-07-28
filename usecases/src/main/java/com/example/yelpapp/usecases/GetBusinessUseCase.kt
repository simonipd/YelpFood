package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import javax.inject.Inject

class GetBusinessUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
){
    operator fun invoke() = businessRepository.business
}