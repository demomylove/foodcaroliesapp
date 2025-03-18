package com.example.foodcalorieapp.data.repository

import com.example.foodcalorieapp.data.local.FoodDao
import com.example.foodcalorieapp.data.model.Food

class FoodRepository(private val foodDao: FoodDao) {

    suspend fun getFoodByName(name: String): Food? {
        return foodDao.getFoodByName(name)
    }
}
