package com.example.foodcalorieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.foodcalorieapp.data.local.FoodDatabase
import com.example.foodcalorieapp.data.repository.FoodRepository
import com.example.foodcalorieapp.utils.AdManager
import com.example.foodcalorieapp.viewmodel.FoodViewModel
import com.example.foodcalorieapp.viewmodel.FoodViewModelFactory
import com.insanejack.foodcalorieapp.databinding.FragmentResultBinding

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val args: ResultFragmentArgs by navArgs()
    private val viewModel: FoodViewModel by viewModels {
        val database = FoodDatabase.getDatabase(requireContext())
        val repository = FoodRepository(database.foodDao())
        FoodViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 获取食物名称并查询卡路里
        val foodName = args.foodName
        viewModel.getFoodByName(foodName)

        // 观察查询结果
        viewModel.food.observe(viewLifecycleOwner) { food ->
            if (food != null) {
                binding.textFoodName.text = "食物: ${food.name}"
                binding.textCalories.text = "卡路里: ${food.calories} kcal/100g"
            } else {
                binding.textFoodName.text = "未找到食物: $foodName"
                binding.textCalories.text = "卡路里: 未知"
            }
        }

        // 显示广告
        AdManager.showBannerAd(binding.adView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
