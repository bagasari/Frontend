package com.example.frontend.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.frontend.R
import com.example.frontend.databinding.ActivityLoginBinding
import com.example.frontend.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private val TAG:String = "SingUpActivity"
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()
            val passwordRe = binding.etPasswordRe.text.toString()
        }
    }
}