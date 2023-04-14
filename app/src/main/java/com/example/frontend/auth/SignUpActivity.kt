package com.example.frontend.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.HomeActivity
import com.example.frontend.databinding.ActivitySignUpBinding
import com.example.frontend.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    private val TAG:String = "SignUpActivity"
    private lateinit var binding: ActivitySignUpBinding
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRe = binding.etPasswordRe.text.toString()
            val intent = Intent(this, HomeActivity::class.java)

            // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                try {
                    // 회원가입 요청
                    val response = retrofit.create(UserService::class.java).postSignUp(email,password,name,"ROLE")
                    if (response.isSuccessful) {
                        val signUpResponse = response.body()
                        Log.d(TAG, "회원가입 성공 $signUpResponse")
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "회원가입 실패 ${response.body()}")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "API 호출 실패")
                }
            }
        }
    }
}