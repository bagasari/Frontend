package com.example.frontend.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.HomeActivity
import com.example.frontend.databinding.ActivityLoginBinding
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


/** 로그인 화면. (이메일,비밀번호)를 입력받고 확인하여 진행 **/
class LoginActivity : AppCompatActivity() {
    private val TAG: String = "LoginActivity"
    private lateinit var binding: ActivityLoginBinding
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Login 버튼. 입력 정보 확인 후 Home 화면으로 이동
        binding.btnLogin.setOnClickListener {
            Log.d(TAG, "로그인 버튼 클릭")

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("FRAG_NUM", "home")
            }
//            // [YHJ 4/17] 로그인 임시 수정
//            startActivity(intent)

            // TODO(): 이메일 형식과 비밀번호 형식을 확인하는 기능 필요

            // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
            val scope = CoroutineScope(Job() + Dispatchers.IO)
            scope.launch {
                try {
                    // 로그인 요청
                    val response = retrofit.create(UserService::class.java).postSignIn(User(email = email,password = password))
                    if (response.isSuccessful) {
                        Log.d(TAG, "로그인 성공 ${response.body()}")
                        val json = response.body()
                        val jsonObject = JsonParser.parseString(json).asJsonObject
                        val token = jsonObject.get("token").asString
                        RetrofitClient.setAccessToken(token)
                        startActivity(intent)

                    } else {
                        val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                        val errorCode = errorBody.optString("code")
                        Log.d(TAG, "로그인 실패 $errorBody")
                        withContext(Dispatchers.Main){
                            when (errorCode) {
                                "A002" -> Utils.showToast(this@LoginActivity,"존재하지 않는 이메일")
                                "A003" -> Utils.showToast(this@LoginActivity,"잘못된 비밀번호")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.d(TAG, "API 호출 실패 $e")
                }
            }
        }

        // 회원가입 버튼. SignUp 화면으로 이동
        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
