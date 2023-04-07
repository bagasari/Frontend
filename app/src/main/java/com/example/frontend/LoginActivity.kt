package com.example.frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    private val tag:String = "LoginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSignIn = findViewById<Button>(R.id.activity_login_btnSignIn)
        btnSignIn.setOnClickListener {
            Log.d(tag, "로그인 버튼 클릭")

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val btnSignUp = findViewById<Button>(R.id.activity_login_btnSignUp)
        btnSignUp.setOnClickListener {
            Log.d(tag, "회원가입 버튼 클릭")
        }

    }
}