package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.adapter.AccountBookAdapter
import com.example.frontend.adapter.HorizontalAccountBookAdapter
import com.example.frontend.databinding.FragAccountBookBinding
import com.example.frontend.databinding.FragHomeBinding
import com.example.frontend.dto.AccountBook
import java.util.*

// 홈 화면
class HomeFragment : Fragment(R.layout.frag_home) {

    private lateinit var binding: FragHomeBinding

    // fragment 바인딩
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragHomeBinding = FragHomeBinding.bind(view)
        binding = fragHomeBinding

        val horizontalAdapter = HorizontalAccountBookAdapter()
        binding.rvMyAccountBookHt.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMyAccountBookHt.adapter = horizontalAdapter

//        binding.btnCreateAccountBook.setOnClickListener {
//
//            val intent = Intent(context, SelectDestinationActivity::class.java)
//            startActivity(intent)
//        }
    }
}