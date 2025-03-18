package com.example.foodcalorieapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodcalorieapp.data.model.Food

@Dao
interface FoodDao {

    @Insert
    suspend fun insert(food: Food)

    @Query("SELECT * FROM food WHERE name = :name")
    suspend fun getFoodByName(name: String): Food?


}
