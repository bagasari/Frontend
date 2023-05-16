package com.example.frontend.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.SelectDestinationActivity
import com.example.frontend.accountBook.ProductsByDate
import com.example.frontend.adapter.ExpenditureAdapter
import com.example.frontend.databinding.FragHomeBinding
import com.example.frontend.dto.AccountBook
import com.example.frontend.dto.Expenditure
import com.example.frontend.dto.Product
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*

// 홈 화면
class HomeFragment : Fragment(R.layout.frag_home) {

    private lateinit var binding: FragHomeBinding
    var date: String = "0"

    // fragment 바인딩
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragHomeBinding = FragHomeBinding.bind(view)
        binding = fragHomeBinding

        binding.btnHomeSearch.setOnClickListener{

            val intent = Intent(context, HomeActivity::class.java).apply{
                putExtra("FRAG_NUM", "search")
            }
            startActivity(intent)
        }

        // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
        val accountBookList = arrayListOf<AccountBook>(
            // 0번째 가계부 생성 넣어줘야 함
            AccountBook(0, "가계부 생성", Date(2022,8,1), Date(2023, 12,12), R.drawable.japan,true, 0),
            AccountBook(1, "동남아 여행", Date(2022, 8, 13), Date(2022, 9, 12), R.drawable.bali, true, 0),
            AccountBook(2, "일본 여행", Date(2023, 2, 13), Date(2023, 2, 19), R.drawable.japan, true, 0)
        )

       // 회원 가계부 리스트 리사이클러뷰 생성
        val horizontalAdapter = HorizontalAccountBookAdapter(accountBookList)
        binding.rvMyAccountBookHt.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMyAccountBookHt.adapter = horizontalAdapter

        // 현재 작성중인 가계부 텍스트 정보 저장

        // 현재 작성중인 가계부 지출 내역 리사이클러뷰 생성
        val dateList = arrayListOf<ProductsByDate>(
            ProductsByDate("20", arrayListOf<Product>(
                Product("1", 1000, "도쿄", "Transportation"),
                Product("2", 2000, "홍콩", "Food")
            )),
            ProductsByDate("21", arrayListOf<Product>(
                Product("꼬치구이", 3000, "대만", "Food"),
                Product("2", 400, "도쿄", "Transportation"),
                Product("3", 3000, "홍콩", "Food")
            ))
        )

        // 현재 작성중인 가계부 지출 내역 날짜 리사이클러뷰
        val horizontalDateAdapter = HorizontalDateAdapter(dateList)
        binding.rvTravelDate.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTravelDate.adapter = horizontalDateAdapter

        // 날짜 리사이클러뷰 요소 선택 시 - 해당 날짜에 대한 지출 내역 리사이클러뷰로 교체
        horizontalDateAdapter.setItemClickListener(object: HorizontalDateAdapter.OnItemClickListener{
            override fun onClick(v:View, position: Int){
                Log.d("date", position.toString())
                val expenditureAdaptor = ExpenditureAdapter(dateList.get(position).products)
                binding.rvWritingAccountBook.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvWritingAccountBook.adapter = expenditureAdaptor
            }
        })

        // 날짜 리사이클러뷰 요소 선택 시 - 해당 날짜에 대한 지출 내역 리사이클러뷰로 교체
        val expenditureAdaptor = ExpenditureAdapter(dateList.get(0).products)
        binding.rvWritingAccountBook.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvWritingAccountBook.adapter = expenditureAdaptor



    }
}