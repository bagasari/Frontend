package com.example.frontend.expenditure

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityExpenditureBinding
import com.example.frontend.retrofit.RetrofitClient
import kotlinx.coroutines.*
import org.json.JSONObject
import kotlin.collections.ArrayList

// 지출 내역 리스트
class ExpenditureActivity: AppCompatActivity() {

    private lateinit var binding: ActivityExpenditureBinding
    private lateinit var getExpenditureDTO: GetExpenditureDTO

    private lateinit var expendList: List<ProductsByDate>
    private lateinit var totalList: ArrayList<Product>
    private lateinit var dateList: ArrayList<String>
    private lateinit var dateArray: Array<String>

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    private val CREATE_EXPENDITURE_REQUEST_CODE = 1 // 요청 코드 정의
    private var accountBookId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenditureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountBookId = intent.getStringExtra("AccountBookId")!!.toLong()

        // 날짜 요소 선택 시 -
        val expenditureAdaptor = ExpenditureAdapter()
        getExpenditureByAccountBookId(accountBookId, expenditureAdaptor, binding)

        binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvExpenditure.adapter = expenditureAdaptor

        binding.productBtnByDate.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("날짜별 조회")
                .setItems(dateArray,
                    DialogInterface.OnClickListener{ dialog, which ->
                        if(which != dateArray.size-1){
                            expenditureAdaptor.setData(expendList[which].products as ArrayList<Product>)
//
//                            val expenditureAdaptor = ExpenditureAdapter(expendList[which].products)
                            binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.rvExpenditure.adapter = expenditureAdaptor
                            binding.productBtnByDate.text = expendList.get(which).purchaseDate.substring(5)
                        }else{

                            expenditureAdaptor.setData(totalList)
//                            val expenditureAdaptor = ExpenditureAdapter(totalList.toList())
                            binding.rvExpenditure.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                            binding.rvExpenditure.adapter = expenditureAdaptor
                            binding.productBtnByDate.text = "기간 전체"
                        }
                    })
            builder.show()
        }

        binding.productBtnAddExpend.setOnClickListener{
            val intent = Intent(this@ExpenditureActivity, CreateExpenditureActivity::class.java)
            Log.d("accountBookId", accountBookId.toString())
            intent.putExtra("AccountBookId", accountBookId.toString())
            startActivityForResult(intent, CREATE_EXPENDITURE_REQUEST_CODE) // startActivityForResult()로 액티비티 시작
        }

    }


    override fun onResume() {
        super.onResume()

        Log.d("ExpenditureActivity", "onResume")
        val handler = Handler()
        handler.postDelayed({
            // 딜레이 이후 실행할 코드 작성
            val expenditureAdapter = binding.rvExpenditure.adapter as ExpenditureAdapter
            getExpenditureByAccountBookId(accountBookId, expenditureAdapter, binding)
        }, 100)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_EXPENDITURE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // 갱신 작업 수행
            val expenditureAdaptor = binding.rvExpenditure.adapter as ExpenditureAdapter
            getExpenditureByAccountBookId(accountBookId, expenditureAdaptor, binding)
        }
    }



    fun getExpenditureByAccountBookId(accountBookId: Long, expenditureAdapter: ExpenditureAdapter, binding: ActivityExpenditureBinding){
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(ExpenditureService::class.java).getExpenditureByAccountBookId(accountBookId)
                if (response.isSuccessful) {
                    Log.d("Expenditure", "지출 내역 통신 성공 ${response.body()}")
                    getExpenditureDTO = response.body()!!
                    withContext(Dispatchers.Main) {
                        // 가계부 정보를 얻은 후에 Adapter에 데이터를 설정
                        Log.d("Expenditure", getExpenditureDTO.toString())
                        expendList = getExpenditureDTO.productsByDate
                        dateList = arrayListOf()
                        totalList = arrayListOf()
                        // 기간 전체 날짜 리스트
                        for(productsByDate in expendList){
                            dateList.add(productsByDate.purchaseDate)
                            for(product in productsByDate.products){
                                totalList.add(product)
                            }
                        }

                        totalList.reverse()
                        dateList.add("기간 전체")
                        dateArray = dateList.toTypedArray()

                        expenditureAdapter.setData(totalList)


                        // 총 지출 금액 setText
                        binding.tvTotalPrice.text = getExpenditureDTO.totalPrice.toString()
                    }

                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("Expenditure", "지출 내역 통신 실패 $errorBody")
                    withContext(Dispatchers.Main){
                    }
                }
            } catch (e: Exception) {
                Log.d("Expenditure", "API 호출 실패 $e")
            }
        }
    }

}

