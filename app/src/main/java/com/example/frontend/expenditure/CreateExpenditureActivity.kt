package com.example.frontend.expenditure

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityCreateExpenditureBinding
import java.util.*

class CreateExpenditureActivity: AppCompatActivity(){

    private lateinit var binding: ActivityCreateExpenditureBinding
    private lateinit var purchaseDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateExpenditureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 시작 날짜 입력 받기 DatePicker
        binding.btnPurchaseDate.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener{
                    view, year, month, day ->
                purchaseDate = "${year}-${month+1}-${day}"
            }
            DatePickerDialog(this, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        binding.ibFood.setOnClickListener{
            val productName = binding.etProductName.text.toString()
            val productPrice = binding.etProductPrice.text.toString()
            val productDetail = binding.etProductDetail.text.toString()

            val intent = Intent(this@CreateExpenditureActivity, FoodFormActivity::class.java)

            intent.putExtra("productName", productName)
            intent.putExtra("productPrice", productPrice)
            intent.putExtra("productDetail", productDetail)
            intent.putExtra("purchaseDate", purchaseDate)

            startActivity(intent)

        }



        binding.ibTransportation.setOnClickListener{
            val intent = Intent(this@CreateExpenditureActivity, TransportationFormActivity::class.java)
            startActivity(intent)
        }


    }

}