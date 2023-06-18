package com.example.frontend.accountBook

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityMakeAccountBookBinding
import com.example.frontend.home.HomeActivity
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import kotlinx.coroutines.*
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class CreateAccountBook : AppCompatActivity() {

    private lateinit var binding: ActivityMakeAccountBookBinding
    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var bookName: String
    private var isPrivate: Boolean = false

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity_make_account_book.xml 바인딩
        binding = ActivityMakeAccountBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 여행지 선택 페이지에서 여행지 리스트 받아와야 함


        // 시작 날짜 입력 받기 DatePicker
        binding.startDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val formattedMonth = String.format("%02d", month + 1) // 월을 2자리로 포맷팅
                val formattedDay = String.format("%02d", dayOfMonth) // 일을 2자리로 포맷팅
                startDate = "$year-$formattedMonth-$formattedDay"
            }
            DatePickerDialog(
                this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.endDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val formattedMonth = String.format("%02d", month + 1) // 월을 2자리로 포맷팅
                val formattedDay = String.format("%02d", dayOfMonth) // 일을 2자리로 포맷팅
                endDate = "$year-$formattedMonth-$formattedDay"
            }
            DatePickerDialog(
                this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        // isPrivate 여부 입력 받기
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                isPrivate = false
            }else{
                isPrivate = true
            }

        }

        binding.button.setOnClickListener{
            bookName = binding.etNewAccountBook.text.toString()

            // 생성하기 버튼 누를 시 api 요청
            Toast.makeText(this, "가계부 이름 : " + bookName + " , 시작 날짜 : " + startDate + ", 종료 날짜 : " + endDate, Toast.LENGTH_SHORT).show()
            val destList = intent.getStringArrayListExtra("selectedDestList") as ArrayList<String>
            val dList: List<String> = destList.toList()
            val postAccountBook = PostAccountBookDTO(bookName, startDate, endDate, isPrivate, dList)
            postNewAccountBook(postAccountBook)
            setResult(RESULT_OK)
            finish()
//
        }
    }

    private fun postNewAccountBook(postAccountBook: PostAccountBookDTO){

        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        // Log.d("test", postAccountBook.name + ", " + postAccountBook.startDate + postAccountBook.cityList.toString())
        scope.launch {
            try {
                // 로그인 요청
               // val post: PostAccountBookDTO = PostAccountBookDTO("","","",true,postAccountBook.cityList)
                val response = retrofit.create(AccountService::class.java).createAccountBook(postAccountBook)
                if (response.isSuccessful) {
                    Log.d("test", "가계부 생성 통신 성공 ${response.body()}")
                    val accountBookDTO = response.body()


                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("test", "가계부 생성 통신 실패 $errorBody")
                    withContext(Dispatchers.Main){
                        when (errorCode) {
                            "A002" -> Utils.showToast(this@CreateAccountBook,"존재하지 않는 이메일")
                            "A003" -> Utils.showToast(this@CreateAccountBook,"잘못된 비밀번호")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("test", "API 호출 실패 $e")
            }
        }
    }
}