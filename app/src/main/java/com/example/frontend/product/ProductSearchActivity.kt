package com.example.frontend.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityProductSearchBinding

class ProductSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}