package com.example.frontend.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.HomeActivity
import com.example.frontend.databinding.ActivityLoginBinding
import com.example.frontend.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/** 로그인 화면. (이메일,비밀번호)를 입력받고 확인하여 진행 **/
class LoginActivity : AppCompatActivity() {
    private val TAG:String = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login 버튼. 입력 정보 확인 후 Home 화면으로 이동
        binding.btnLogin.setOnClickListener{
            Log.d(TAG, "로그인 버튼 클릭")

            // 임시 : "email":"sac@naver.com", "password":"sac_pwd"
            val email = binding.etEmail.text.toString()
            val pw = binding.etPassword.text.toString()
            val intent = Intent(this, HomeActivity::class.java)

            // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
            val scope = CoroutineScope(Job()+Dispatchers.IO)
            scope.launch {
                try {
                    // 로그인 요청
                    val response = retrofit.create(UserService::class.java).postSignIn(email,pw)
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        Log.d(TAG, "로그인 성공 $loginResponse")
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "로그인 실패 ${response.body()}")
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "API 호출 실패")
                }
            }

            startActivity(intent) // 삭제 예정
        }

        // 회원가입 버튼. SignUp 화면으로 이동
        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}