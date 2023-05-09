package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.auth.ProductAdapter
import com.example.frontend.auth.TestProduct
import com.example.frontend.databinding.ActivityProductSearchBinding

// 상품 검색 액티비티
class ProductSearchActivity: AppCompatActivity() {
    private val TAG: String = "ProductSearchActivity"
    private lateinit var binding: ActivityProductSearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var productList = ArrayList<TestProduct>()
        productList.add(TestProduct(name = "1", price = "10"))
        productList.add(TestProduct(name = "2", price = "10"))
        productList.add(TestProduct(name = "3", price = "10"))
        productList.add(TestProduct(name = "4", price = "10"))
        productList.add(TestProduct(name = "5", price = "10"))
        productList.add(TestProduct(name = "6", price = "10"))
        productList.add(TestProduct(name = "7", price = "10"))
        productList.add(TestProduct(name = "8", price = "10"))
        productList.add(TestProduct(name = "9", price = "10"))

        binding.productSearchRv.apply {
            layoutManager = LinearLayoutManager(this@ProductSearchActivity)
            adapter = ProductAdapter(productList)
        }
    }
}