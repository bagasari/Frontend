package com.example.frontend

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityMakeAccountBookBinding
import com.example.frontend.home.HomeActivity
import java.util.*

class CreateAccountBook : AppCompatActivity() {

    private lateinit var binding: ActivityMakeAccountBookBinding
    private lateinit var startDate: String
    private lateinit var endDate: String
    private lateinit var bookName: String
    private var isPublic: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // activity_make_account_book.xml 바인딩
        binding = ActivityMakeAccountBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 이전 여행지 선택 페이지에서 여행지 리스트 받아와야 함


        // 시작 날짜 입력 받기 DatePicker
        binding.startDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener{
              view, year, month, day ->
                startDate = "${year}-${month+1}-${day}"
            }
            DatePickerDialog(this, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // 종료 날짜 입력 받기 DatePicker
        binding.endDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener{
                    view, year, month, day ->
                endDate = "${year}-${month+1}-${day}"
            }
            DatePickerDialog(this, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        // isPrivate 여부 입력 받기
        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                isPublic = true
            }else{
                isPublic = false
            }

        }

        binding.button.setOnClickListener{
            bookName = binding.etNewAccountBook.text.toString()
            lateinit var isP : String
            if(isPublic){
                isP = "public"
            }else{
                isP = "private"
            }

            // 생성하기 버튼 누를 시 api 요청
            Toast.makeText(this, "가계부 이름 : " + bookName + " , 시작 날짜 : " + startDate + ", 종료 날짜 : " + endDate + ", 공개 여부 : " + isP, Toast.LENGTH_SHORT).show()

            // 생성 완료 시 가계부 목록으로 이동
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("FRAG_NUM", "accountBook")
            }
            startActivity(intent)
        }


    }
}