package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** 로그인 화면. (이메일,비밀번호)를 입력받고 확인하여 진행 **/
class LoginActivity : AppCompatActivity() {
    private val TAG:String = "LoginActivity"
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignIn = findViewById<Button>(R.id.activity_login_btnLogin)
        val btnSignUp = findViewById<Button>(R.id.activity_login_btnSignUp)
        val etEmail = findViewById<EditText>(R.id.activity_login_etEmail)
        val etPassword = findViewById<EditText>(R.id.activity_login_etPassword)

        // Login 버튼. 입력 정보 확인 후 Home 화면으로 이동
        btnSignIn.setOnClickListener {
            Log.d(TAG, "로그인 버튼 클릭")

            var email = etEmail.text.toString()
            var pw = etPassword.text.toString()

            val userService = retrofit.create(UserService::class.java)
            userService.getSignIn(email,pw)
                .enqueue(object: Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if(response.isSuccessful.not())
                            return
                        response.body()?.let{
                            Log.d(TAG, it.toString())
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.d(TAG, t.toString())
                    }

                })


            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }

        // 회원가입 버튼. SignUp 화면으로 이동
        btnSignUp.setOnClickListener {
            Log.d(TAG, "회원가입 버튼 클릭")

            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}