package com.example.frontend.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductBinding

// 상품 검색 액티비티
class ProductActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productList = ArrayList<TestProduct>()
        productList.add(TestProduct(name = "1", price = "10"))
        productList.add(TestProduct(name = "2", price = "10"))
        productList.add(TestProduct(name = "3", price = "10"))
        productList.add(TestProduct(name = "4", price = "10"))
        productList.add(TestProduct(name = "5", price = "10"))
        productList.add(TestProduct(name = "6", price = "10"))
        productList.add(TestProduct(name = "7", price = "10"))
        productList.add(TestProduct(name = "8", price = "10"))
        productList.add(TestProduct(name = "9", price = "10"))

        binding.productRv.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = ProductAdapter(productList)
        }

        binding.productBtnMap.setOnClickListener {
            val intent = Intent(this, ProductMapActivity::class.java)
            startActivity(intent)
        }

        binding.productBtnSearchProduct.setOnClickListener {
            val intent = Intent(this, ProductSearchActivity::class.java)
            startActivity(intent)
        }
    }
}