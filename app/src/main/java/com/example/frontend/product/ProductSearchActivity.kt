package com.example.frontend.product

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductSearchBinding
import com.example.frontend.dto.Destination
import java.util.ArrayList

class ProductSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductSearchBinding
    private lateinit var productSearchAdapter: ProductSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dummy 삭제 예정 - 원래는 비어 있어야 함
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


        // 품목 리스트
        productSearchAdapter = ProductSearchAdapter(productList = productList)
        binding.productSearchRv.apply {
            layoutManager = LinearLayoutManager(this@ProductSearchActivity)
            adapter = productSearchAdapter
        }

        // 품목 검색창
        binding.productSearchSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                // 검색어 입력하고 엔터 키 눌렀을 때 호출
                performSearch(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                // 검색어가 변결될 때마다 호출
                performSearch(newText)
                return true
            }
        })
    }

    private fun performSearch(query: String){
        // 구현 예정: 서버에 검색어를 전달하고 필터링된 데이터를 받아서 다시 설정
        val newProductList = ArrayList<TestProduct>()
        newProductList.add(TestProduct(name = "1", price = "10"))
        newProductList.add(TestProduct(name = "2", price = "10"))
        productSearchAdapter.setProductList(newProductList)

    }

}