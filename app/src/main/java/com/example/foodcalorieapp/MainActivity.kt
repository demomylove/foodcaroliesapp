package com.example.foodcalorieapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.foodcalorieapp.utils.AdManager
import com.example.foodcalorieapp.utils.ImageClassifier
import com.insanejack.foodcalorieapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化导航
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // 初始化 AdMob
        AdManager.initialize(this)

        // 初始化 TensorFlow Lite 模型
        //ImageClassifier.initialize(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭 TensorFlow Lite 模型
        ImageClassifier.close()
    }
}
