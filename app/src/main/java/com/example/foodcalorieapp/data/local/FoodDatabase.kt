package com.example.foodcalorieapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodcalorieapp.data.model.Food
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    private fun initializeData(database: FoodDatabase) {
        val dao = database.foodDao()
        CoroutineScope(Dispatchers.IO).launch {
            dao.insert(Food(1, "苹果", 52))
            dao.insert(Food(2, "香蕉", 89))
            dao.insert(Food(3, "披萨", 266))
            dao.insert(Food(4, "米饭", 130))
            dao.insert(Food(5, "鸡肉", 165))
        }
    }
    //在getDatabase方法中调用initializeData：


    fun getDatabase(context: Context): FoodDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                FoodDatabase::class.java,
                "food_database"
            ).build()
            INSTANCE = instance
            initializeData(instance) // 初始化数据
            instance
        }
    }

}
