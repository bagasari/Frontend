package com.example.frontend.auth

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivitySignUpBinding
import com.example.frontend.retrofit.RetrofitClient
import com.example.frontend.utils.Utils
import kotlinx.coroutines.*
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private val TAG:String = "SignUpActivity"
    private lateinit var binding: ActivitySignUpBinding
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etName.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val name = binding.etName.text.toString()
                if (!checkName(name)) {
                    binding.etName.error = "3~50자의 아이디를 입력하세요."
                } else {
                    binding.etName.error = null
                }
            }
        }

        binding.etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val email = binding.etEmail.text.toString()
                if (!checkEmail(email)) {
                    binding.etEmail.error = "이메일 형식이 올바르지 않습니다."
                } else {
                    binding.etEmail.error = null
                }
            }
        }

        binding.etPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.etPassword.text.toString()
                if (!checkPassword(password)) {
                    binding.etPassword.error = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하도록 입력하세요."
                } else {
                    binding.etPassword.error = null
                }
            }
        }

        binding.etPasswordRe.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val password = binding.etPassword.text.toString()
                val passwordRe = binding.etPasswordRe.text.toString()
                if (!checkPasswordRe(password, passwordRe)) {
                    binding.etPasswordRe.error = "비밀번호가 일치하지 않습니다."
                } else {
                    binding.etPasswordRe.error = null
                }
            }
        }


        binding.btnSignUp.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRe = binding.etPasswordRe.text.toString()

            if(checkEmail(email) && checkName(name) && checkPassword(password) && checkPasswordRe(password, passwordRe))
            {
                // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
                val scope = CoroutineScope(Job() + Dispatchers.IO)
                scope.launch {
                    try {
                        // 회원가입 요청
                        val response = retrofit.create(UserService::class.java).postSignUp(User(email = email,password = password,name = name))
                        if (response.isSuccessful) {
                            val signUpResponse = response.body()
                            Log.d(TAG, "회원가입 성공 $signUpResponse")
                            finish()
                        } else {
                            val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                            Log.d(TAG, "회원가입 실패 $errorBody")
                            val errorCode = errorBody.optString("code")
                            if (errorCode == "A001") {
                                withContext(Dispatchers.Main) {
                                    Utils.showToast(this@SignUpActivity,"이미 존재하는 회원입니다.")
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.d(TAG, "API 호출 실패 $e")
                    }
                }
            }
        }
    }
    private fun checkName(name: String): Boolean {
        return name.length in 3..50
    }

    private fun checkEmail(email: String): Boolean {
        val regexPattern = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        return email.matches(regexPattern)
    }

    private fun checkPassword(password: String): Boolean {
        val regexPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()?\\-_=+\\|])[A-Za-z\\d!@#$%^&*()?\\-_=+\\|]{8,16}\$")
        return password.matches(regexPattern)
    }

    private fun checkPasswordRe(password: String, passwordRe: String): Boolean {
        return password == passwordRe
    }
}