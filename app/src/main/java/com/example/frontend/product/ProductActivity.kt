package com.example.frontend.product

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductBinding
import com.example.frontend.databinding.FragProductBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.*

// 상품 검색 액티비티
class ProductActivity: AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding
    private var isLatestSelected = true // 최신순, 추천순 선택

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dummy 삭제 예정
        val productList = ArrayList<Product>()
        productList.add(Product(name = "1", price = "10"))
        productList.add(Product(name = "2", price = "10"))
        productList.add(Product(name = "3", price = "10"))
        productList.add(Product(name = "4", price = "10"))
        productList.add(Product(name = "5", price = "10"))
        productList.add(Product(name = "6", price = "10"))
        productList.add(Product(name = "7", price = "10"))
        productList.add(Product(name = "8", price = "10"))
        productList.add(Product(name = "9", price = "10"))

        // 품목 리스트
        binding.productRv.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = ProductAdapter(productList)
        }

        // 지도
        binding.productBtnMap.setOnClickListener {
            val intent = Intent(this@ProductActivity, ProductMapActivity::class.java)
            startActivity(intent)
        }

        // 품목 검색
        binding.productBtnSearchProduct.setOnClickListener {
            val intent = Intent(this@ProductActivity, ProductSearchActivity::class.java)
            startActivity(intent)
        }

        // 품목 날짜 선택
        binding.productBtnDate.setOnClickListener{
               showDateRangePickerDialog{ startDate, endDate ->
                   val date = "$startDate ~ $endDate"
                   binding.productBtnDate.text = date
               }
        }

        // 버튼 초기화 및 클릭 이벤트 설정
        binding.productBtnFilter.setOnClickListener{showBottomSheetMenu()}

    }

    private fun showBottomSheetMenu() {
        val bottomSheetDialog = BottomSheetDialog(this@ProductActivity)
        val bottomSheetBinding = FragProductBottomSheetBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // Bottom Sheet 내부의 View 이벤트 처리
        bottomSheetBinding.productBtnLatest.isSelected = isLatestSelected
        bottomSheetBinding.productBtnRecommend.isSelected = !isLatestSelected

        bottomSheetBinding.productBtnLatest.setOnClickListener {
            // 최신순을 선택한 경우 처리
            isLatestSelected = !isLatestSelected
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.productBtnRecommend.setOnClickListener {
            // 추천순을 선택한 경우 처리
            isLatestSelected = !isLatestSelected
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
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