package com.example.frontend.expenditure

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityTransportationFormBinding

class TransportationFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityTransportationFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransportationFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}