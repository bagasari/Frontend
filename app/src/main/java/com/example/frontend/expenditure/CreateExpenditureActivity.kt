package com.example.frontend.expenditure

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.accountBook.AccountService
import com.example.frontend.accountBook.PostAccountBookDTO
import com.example.frontend.databinding.ActivityCreateExpenditureBinding
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*

class CreateExpenditureActivity: AppCompatActivity(){

    private lateinit var binding: ActivityCreateExpenditureBinding
    private lateinit var purchaseDate: String

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateExpenditureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accountBookId = intent.getStringExtra("AccountBookId")!!.toLong()

        // 시작 날짜 입력 받기 DatePicker
        binding.btnPurchaseDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val formattedMonth = String.format("%02d", month + 1) // 월을 2자리로 포맷팅
                val formattedDay = String.format("%02d", dayOfMonth) // 일을 2자리로 포맷팅
                purchaseDate = "$year-$formattedMonth-$formattedDay"
            }
            DatePickerDialog(
                this,
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.ibFood.setOnClickListener{
            val productName = binding.etProductName.text.toString()
            val productPrice = binding.etProductPrice.text.toString()
            val productDetail = binding.etProductDetail.text.toString()

            val intent = Intent(this@CreateExpenditureActivity, FoodFormActivity::class.java)

            intent.putExtra("AccountBookId", accountBookId.toString())
            intent.putExtra("productName", productName)
            intent.putExtra("productPrice", productPrice)
            intent.putExtra("productDetail", productDetail)
            intent.putExtra("purchaseDate", purchaseDate)

            startActivity(intent)
            setResult(RESULT_OK)
            finish()
        }

        binding.ibTransportation.setOnClickListener{
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                FOOD_FORM_REQUEST_CODE -> {
                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }


    companion object {
        private const val FOOD_FORM_REQUEST_CODE = 1
    }

}