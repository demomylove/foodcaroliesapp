package com.example.foodcalorieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodcalorieapp.data.model.Food
import com.example.foodcalorieapp.data.repository.FoodRepository
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    private val _food = MutableLiveData<Food?>()
    val food: LiveData<Food?> get() = _food

    fun getFoodByName(name: String) {
        viewModelScope.launch {
            val result = repository.getFoodByName(name)
            _food.postValue(result)
        }
    }
}
