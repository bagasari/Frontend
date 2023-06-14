package com.example.frontend.expenditure

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.accountBook.ProductsByDate
import com.example.frontend.databinding.ActivityExpenditureBinding
import com.example.frontend.dto.Product
import kotlin.collections.ArrayList

// 지출 내역 리스트
class ExpenditureActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExpenditureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenditureBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
        val expendList = arrayListOf<ProductsByDate>(
            ProductsByDate("2023-03-20", arrayListOf<Product>(
                Product("1", 1000, "도쿄", "Transportation"),
                Product("2", 2000, "홍콩", "Food")
            )),
            ProductsByDate("2023-03-21", arrayListOf<Product>(
                Product("꼬치구이", 3000, "대만", "Food"),
                Product("2", 400, "도쿄", "Transportation"),
                Product("3", 3000, "홍콩", "Food")
            )),
            ProductsByDate("2023-03-22", arrayListOf<Product>(
                Product("꼬치구이", 3000, "대만", "Food"),
                Product("2", 400, "도쿄", "Transportation"),
                Product("3", 3000, "홍콩", "Food")
            )),
            ProductsByDate("2023-03-23", arrayListOf<Product>(
                Product("꼬치구이", 3000, "대만", "Food"),
                Product("2", 400, "도쿄", "Transportation"),
                Product("3", 3000, "홍콩", "Food")
            ))
        )

        // 기간 전체 날짜 리스트
        val totalList: ArrayList<Product> = arrayListOf()
        val dateList: ArrayList<String> = arrayListOf()
        for(productsByDate in expendList){
            dateList.add(productsByDate.purchaseDate)
            for(product in productsByDate.products){
                totalList.add(product)
            }
        }
        totalList.reverse()

        dateList.add("기간 전체")
        val dateArray: Array<String> = dateList.toTypedArray()
        // 기간 다이얼로그 리사이클러뷰

        // 날짜 요소 선택 시 -
        val expenditureAdaptor = ExpenditureAdapter(totalList)
        binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvExpenditure.adapter = expenditureAdaptor

        binding.productBtnByDate.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("날짜별 조회")
                .setItems(dateArray,
                    DialogInterface.OnClickListener{ dialog, which ->
                        if(which != dateArray.size-1){
                            val expenditureAdaptor = ExpenditureAdapter(expendList.get(which).products)
                            binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.rvExpenditure.adapter = expenditureAdaptor
                            binding.productBtnByDate.text = expendList.get(which).purchaseDate.substring(5)
                        }else{
                            val expenditureAdaptor = ExpenditureAdapter(totalList)
                            binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.rvExpenditure.adapter = expenditureAdaptor
                            binding.productBtnByDate.text = "기간 전체"
                        }
                    })
            builder.show()
        }

        binding.productBtnAddExpend.setOnClickListener{
            val intent = Intent(this@ExpenditureActivity, CreateExpenditureActivity::class.java)
            startActivity(intent)
        }

        // 총 지출 금액 setText
        // binding.tvTotalPrice.text = "총 금액"


    }
}