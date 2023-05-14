package com.example.frontend

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.home.HorizontalAccountBookAdapter
import com.example.frontend.databinding.FragHomeBinding
import com.example.frontend.dto.AccountBook
import java.util.*

// 지출 내역 리스트
class ExpenditureActivity: AppCompatActivity() {

    private lateinit var binding: FragHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenditure)

        val rv_expenditure = findViewById<RecyclerView>(R.id.rv_expenditure)
        // 1. 어댑터 생성

        // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
        val accountBookList = arrayListOf<AccountBook>(
            AccountBook(1, "동남아 여행", Date(2022, 8, 13), Date(2022, 9, 12), R.drawable.bali, true, 0),
            AccountBook(2, "일본 여행", Date(2023, 2, 13), Date(2023, 2, 19), R.drawable.japan, true, 0)
        )
        val expenditureAdapter =  HorizontalAccountBookAdapter(accountBookList)
        expenditureAdapter.notifyDataSetChanged()

        rv_expenditure.adapter = expenditureAdapter
        rv_expenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}