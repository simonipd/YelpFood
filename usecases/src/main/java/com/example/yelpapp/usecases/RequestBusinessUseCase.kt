package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import javax.inject.Inject

class RequestBusinessUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
){
    suspend operator fun invoke() = businessRepository.requestBusiness()
}