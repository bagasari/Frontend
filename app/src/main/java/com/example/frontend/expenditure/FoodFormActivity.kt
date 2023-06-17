package com.example.frontend.expenditure

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityFoodFormBinding
import com.example.frontend.home.HomeActivity
import com.example.frontend.retrofit.RetrofitClient
import kotlinx.coroutines.*
import org.json.JSONObject

class FoodFormActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFoodFormBinding

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accountBookId = intent.getStringExtra("AccountBookId")!!.toLong()
        val productName = intent.getStringExtra("productName")!!
        val productPrice = intent.getStringExtra("productPrice")!!
        val productDetail = intent.getStringExtra("productDetail")!!
        val purchaseDate = intent.getStringExtra("purchaseDate")!!

        //food 입력 폼

        Log.e("Food", productName + productPrice + productDetail + purchaseDate)


        val foodCount = binding.etFoodCount.text.toString()
        val foodWeight = binding.etFoodWeight.text.toString()

        // 구글맵 지도에서 좌표 불러오기


        binding.btnSubmit.setOnClickListener{
            val foodCountText = binding.etFoodCount.text.toString()
            val foodWeightText = binding.etFoodWeight.text.toString()

            if (foodCountText.isNotEmpty() && foodWeightText.isNotEmpty() && productPrice.isNotEmpty()) {
                val foodCount = foodCountText.toInt()
                val foodWeight = foodWeightText.toInt()
                val product: PostProduct = PostProduct(accountBookId, productName, productPrice.toInt(), purchaseDate, productDetail, "일본", "도쿄")

                val food: Food = Food(foodCount, foodWeight, "200", "200")
                val expenditureFoodDTO: ExpenditureFoodDTO = ExpenditureFoodDTO(product, food)

                val intent = Intent(this, ExpenditureActivity::class.java).apply {
                    putExtra("userId", "still")
                    putExtra("FRAG_NUM", "accountBook")
                }

                postFoodExpenditure(expenditureFoodDTO)
                finish()
            }
        }
    }


    private fun postFoodExpenditure(expenditureFoodDTO: ExpenditureFoodDTO){

        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                // val post: PostAccountBookDTO = PostAccountBookDTO("","","",true,postAccountBook.cityList)
                val response = retrofit.create(ExpenditureService::class.java).createFoodExpenditure(expenditureFoodDTO)
                if (response.isSuccessful) {
                    Log.d("createExpenditure", "지출내역 전송 성공 ${response.body()}")
                    val isokay = response.body()
                    Log.d("createExpenditure", isokay!!)


                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("createExpenditure", "지출내역 전송 실패 $errorBody")
                    withContext(Dispatchers.Main){
                        when (errorCode) {
                            "C001" -> Log.d("createExpenditure", "존재하지 않는 가계부")
                            "C002" -> Log.d("createExpenditure", "가계부 존재")
                            else -> {
                                Log.d("createExpenditure", "모르겠음")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("createExpenditure", "API 호출 실패 $e")
            }
        }
    }

}