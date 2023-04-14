package com.example.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.FragAccountBookBinding
import java.util.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // setContentView() 한 것임
        val view = inflater.inflate(R.layout.frag_home, container, false)
        return view
    }
}