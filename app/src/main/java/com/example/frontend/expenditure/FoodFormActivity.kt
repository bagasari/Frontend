package com.example.frontend.expenditure

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityFoodFormBinding

class FoodFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFoodFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productName = intent.getStringExtra("productName")
        val productPrice = intent.getStringExtra("productPrice")
        val productDetail = intent.getStringExtra("productDetail")
        val purchaseDate = intent.getStringExtra("purchaseDate")
        Log.e("Food", productName + productPrice + productDetail + purchaseDate)
    }
}