package com.example.frontend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MyAccountBookListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // setContentView() 한 것임
        val view = inflater.inflate(R.layout.frag_account_book, container, false)
        return view
    }
}