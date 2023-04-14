package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.FragAccountBookBinding
import com.example.frontend.dto.AccountBook
import java.util.*

class AccountBookListFragment : Fragment(R.layout.frag_account_book) {

    private lateinit var binding: FragAccountBookBinding

    // fragment 바인딩
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragAccountBookBinding = FragAccountBookBinding.bind(view)
        binding = fragAccountBookBinding

        // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
        val accountBookList = arrayListOf<AccountBook>(
            AccountBook(1, "동남아 여행", Date(2022, 8, 13), Date(2022, 9, 12), R.drawable.bali, true, 0),
            AccountBook(2, "일본 여행", Date(2023, 2, 13), Date(2023, 2, 19), R.drawable.japan, true, 0)
        )

        val accountBookAdapter = AccountBookAdapter(accountBookList)

        binding.rvMyAccountBookListF.layoutManager = LinearLayoutManager(context)
        binding.rvMyAccountBookListF.adapter = accountBookAdapter

        // 회원가입 버튼. SignUp 화면으로 이동
        binding.btnCreateAccountBook.setOnClickListener {

            val intent = Intent(context, SelectDestinationActivity::class.java)
            startActivity(intent)
        }
    }
}