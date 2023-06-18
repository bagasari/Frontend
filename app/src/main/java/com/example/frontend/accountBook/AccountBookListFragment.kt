package com.example.frontend.accountBook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.databinding.FragAccountBookBinding
import com.example.frontend.dto.Destination
import com.example.frontend.expenditure.ExpenditureAdapter
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class AccountBookListFragment : Fragment(R.layout.frag_account_book) {

    private lateinit var binding: FragAccountBookBinding
    private lateinit var accountBookDTO: List<GetAccountBookDTO>

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    private val ACCOUNT_BOOK_LIST_REQUEST_CODE = 1 // 요청 코드 정의

    // fragment 바인딩
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragAccountBookBinding = FragAccountBookBinding.bind(view)
        binding = fragAccountBookBinding


        val bundle = arguments
        val destList = bundle?.getParcelableArrayList<Destination>("destList")?.toList()
        Log.d("test", destList.toString())

        val accountBookAdapter = AccountBookAdapter(emptyList(), destList) // 빈 리스트로 초기화

        binding.rvMyAccountBookListF.layoutManager = LinearLayoutManager(context)
        binding.rvMyAccountBookListF.adapter = accountBookAdapter

        binding.btnCreateAccountBook.setOnClickListener {

            val intent = Intent(context, SelectDestinationActivity::class.java)
            intent.putParcelableArrayListExtra("destList", bundle?.getParcelableArrayList<Destination>("destList"))
            startActivityForResult(intent, ACCOUNT_BOOK_LIST_REQUEST_CODE) // startActivityForResult()로 액티비티 시작
        }

        // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
        getAccountBookList(accountBookAdapter)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACCOUNT_BOOK_LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 갱신 작업 수행
            val accountBookAdapter = binding.rvMyAccountBookListF.adapter as AccountBookAdapter
            // API 통해 회원의 가계부 정보를 받아 accountBookList에 저장
            getAccountBookList(accountBookAdapter)
        }
    }

    fun getAccountBookList(accountBookAdapter: AccountBookAdapter){
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(AccountService::class.java).getAccountBookList()
                if (response.isSuccessful) {
                    Log.d("ACB", "가계부 리스트 통신 성공 ${response.body()}")
                    accountBookDTO = response.body()!!
                    withContext(Dispatchers.Main) {
                        // 가계부 정보를 얻은 후에 Adapter에 데이터를 설정
                        accountBookAdapter.updateData(accountBookDTO)
                        Log.d("ACB", accountBookAdapter.toString())
                    }

                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("ACB", "가계부 리스트 통신 실패 $errorBody")
                    withContext(Dispatchers.Main){
                    }
                }
            } catch (e: Exception) {
                Log.d("ACB", "API 호출 실패 $e")
            }
        }
    }
}