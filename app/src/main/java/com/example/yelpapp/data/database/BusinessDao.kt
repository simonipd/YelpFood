package com.example.yelpapp.data.database

import androidx.room.*
import com.example.yelpapp.data.database.model.Business
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {

    @Query("SELECT * FROM Business ORDER BY name")
    fun getAll(): Flow<List<Business>>

    @Query("SELECT * FROM Business WHERE id = :id")
    fun findById(id: String): Flow<Business>

    @Query("SELECT COUNT(id) FROM Business")
    suspend fun businessCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(business: List<Business>)
}