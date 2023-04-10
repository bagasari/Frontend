package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val btnSignUp = findViewById<Button>(R.id.activity_sign_up_btnSignUp)
        val etEmail = findViewById<EditText>(R.id.activity_sign_up_etEmail)
        val etName = findViewById<EditText>(R.id.activity_sign_up_etName)
        val etPassword = findViewById<EditText>(R.id.activity_sign_up_etPassWord)
        val etPasswordRe = findViewById<EditText>(R.id.activity_sign_up_etPassWordRe)

        btnSignUp.setOnClickListener {
            val email = etEmail.text.toString()
            val name = etName.text.toString()
            val password = etPassword.text.toString()
            val passwordRe = etPasswordRe.text.toString()


        }
    }
}