package com.example.frontend.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.accountBook.*
import com.example.frontend.accountBook.ProductsByDate
import com.example.frontend.expenditure.*
import com.example.frontend.databinding.FragHomeBinding
import com.example.frontend.dto.Destination
import com.example.frontend.retrofit.RetrofitClient
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

// 홈 화면
class HomeFragment : Fragment(R.layout.frag_home) {

    private lateinit var binding: FragHomeBinding
    private lateinit var currentAccountBookDTO: GetCurrentAccountBookDTO
    private lateinit var accountBookDTO: List<GetAccountBookDTO>
    private lateinit var productsByDate: List<ProductsByDate>

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    var date: String = "0"

    // fragment 바인딩
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragHomeBinding = FragHomeBinding.bind(view)
        binding = fragHomeBinding

        val bundle = arguments
        val destList = bundle?.getParcelableArrayList<Destination>("destList")?.toList()
        Log.d("HomeFragment", destList.toString())
        binding.btnHomeSearch.setOnClickListener{

            val intent = Intent(context, HomeActivity::class.java).apply{
                putExtra("FRAG_NUM", "search")
            }
            startActivity(intent)
        }

        val userId = bundle?.getString("name")

        binding.tvUserId.text = userId + "님의 가계부"

       // 회원 가계부 리스트 리사이클러뷰 생성
        val horizontalAccountBookAdapter = HorizontalAccountBookAdapter()
        binding.rvMyAccountBookHt.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMyAccountBookHt.adapter = horizontalAccountBookAdapter



        // 현재 작성중인 가계부 지출 내역 날짜 리사이클러뷰
        val horizontalDateAdapter = HorizontalDateAdapter(emptyList())
        binding.rvTravelDate.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTravelDate.adapter = horizontalDateAdapter

        // 날짜 리사이클러뷰 요소 선택 시 - 해당 날짜에 대한 지출 내역 리사이클러뷰로 교체
        horizontalDateAdapter.setItemClickListener(object: HorizontalDateAdapter.OnItemClickListener{
            override fun onClick(v:View, position: Int){
                Log.d("date", position.toString())
                val expenditureAdaptor = ExpenditureAdapter()
                expenditureAdaptor.setData(productsByDate[position].products as ArrayList<Product>)
                binding.rvWritingAccountBook.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvWritingAccountBook.adapter = expenditureAdaptor
            }
        })

        // 날짜 리사이클러뷰 요소 선택 시 - 해당 날짜에 대한 지출 내역 리사이클러뷰로 교체
        val expenditureAdaptor = ExpenditureAdapter()
        binding.rvWritingAccountBook.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvWritingAccountBook.adapter = expenditureAdaptor


        getAccountBookList(horizontalAccountBookAdapter, destList!!)
        // API 통해 회원의 현재 작성중인 가계부 정보를 받아 currentAccountBookDTO에 저장
        getCurrentAccountBookList(horizontalDateAdapter,expenditureAdaptor, binding)
    }

    fun settingText(binding: FragHomeBinding){
        binding.tvWritingAccountBook.text = currentAccountBookDTO.name
        binding.tvStartDate.text = currentAccountBookDTO.startDate
        binding.tvEndDate.text = currentAccountBookDTO.endDate
        binding.tvTotalPrice.text = currentAccountBookDTO.totalPrice.toString()

    }

    fun getAccountBookList(horizontalAccountBookAdapter: HorizontalAccountBookAdapter, destList: List<Destination>){
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(AccountService::class.java).getAccountBookList()
                if (response.isSuccessful) {
                    Log.d("ACB", "가계부 리스트 통신 성공 ${response.body()}")
                    accountBookDTO = response.body()!!
                    val tempArray: ArrayList<GetAccountBookDTO> = arrayListOf()
                    tempArray.add(GetAccountBookDTO(0,"가계부 생성","","",""))
                    tempArray.addAll(accountBookDTO)
                    withContext(Dispatchers.Main) {
                        // 가계부 정보를 얻은 후에 Adapter에 데이터를 설정
                        horizontalAccountBookAdapter.setData(tempArray,
                            destList.toMutableList() as ArrayList<Destination>
                        )
                        Log.d("ACB", horizontalAccountBookAdapter.toString())
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

    fun getCurrentAccountBookList(horizontalDateAdapter: HorizontalDateAdapter, expenditureAdapter: ExpenditureAdapter, binding: FragHomeBinding){
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(AccountService::class.java).getCurrentAccount()
                if (response.isSuccessful) {
                    Log.d("HF", "현재 가계부 요청 통신 성공 ${response.body()}")
                    currentAccountBookDTO = response.body()!!
                    productsByDate = currentAccountBookDTO.productsByDate
                    withContext(Dispatchers.Main) {
                        // 가계부 정보를 얻은 후에 Adapter에 데이터를 설정
                        horizontalDateAdapter.updateData(productsByDate)
                        if(productsByDate.isNotEmpty())
                            expenditureAdapter.setData(productsByDate[0].products as ArrayList<Product>)
                        // 현재 작성중인 가계부 텍스트 정보 저장
                        settingText(binding)
                    }

                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("HF", "현재 가계부 요청 통신 실패 $errorBody")
                    withContext(Dispatchers.Main){
                    }
                }
            } catch (e: Exception) {
                Log.d("HF", "API 호출 실패 $e")
            }
        }
    }
}