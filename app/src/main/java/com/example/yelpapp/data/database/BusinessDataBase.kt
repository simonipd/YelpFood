package com.example.yelpapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.yelpapp.data.database.model.Business

@Database(entities = [Business::class], version = 1, exportSchema = false)
abstract class BusinessDataBase: RoomDatabase() {

    abstract fun businessDao(): BusinessDao
}