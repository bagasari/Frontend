package com.example.frontend.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

// 상품 검색 액티비티
class ProductActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dummy 삭제 예정
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
        binding.productRv.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = ProductAdapter(productList)
        }

        // 지도
        binding.productBtnMap.setOnClickListener {
            val intent = Intent(this, ProductMapActivity::class.java)
            startActivity(intent)
        }

        // 품목 검색
        binding.productBtnSearchProduct.setOnClickListener {
            val intent = Intent(this, ProductSearchActivity::class.java)
            startActivity(intent)
        }

        // 품목 날짜 선택
        binding.productBtnDate.setOnClickListener{
               showDateRangePickerDialog{ startDate, endDate ->
                   val date = "$startDate ~ $endDate"
                   binding.productBtnDate.text = date
               }
        }
    }

    private fun showDateRangePickerDialog(onDateRangeSet: (String, String) -> Unit) {

        val datePickerDialog = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("날짜를 선택해주세요.")
            .setSelection(
                androidx.core.util.Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()

        datePickerDialog.addOnPositiveButtonClickListener { dateRange ->
            // 선택한 기간 처리
            val startDate = convertDateToString(dateRange.first)
            val endDate = convertDateToString(dateRange.second)
            onDateRangeSet(startDate, endDate)
        }

        datePickerDialog.show(supportFragmentManager, "DateRangePickerDialog")
    }

    private fun convertDateToString(date: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%04d-%02d-%02d", year, month, day)
    }
}