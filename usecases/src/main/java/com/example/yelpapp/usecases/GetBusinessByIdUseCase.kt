package com.example.yelpapp.usecases

import com.example.yelpapp.data.repository.BusinessRepository
import javax.inject.Inject

class GetBusinessByIdUseCase @Inject constructor(
    private val businessRepository: BusinessRepository
){
    operator fun invoke(id : String) = businessRepository.getBusinessById(id)
}