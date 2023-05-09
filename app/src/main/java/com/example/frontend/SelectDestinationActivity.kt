package com.example.frontend

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.adapter.DestAdapter
import com.example.frontend.dto.Destination

// 가계부 생성 시 국가 선택 액티비티
class SelectDestinationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var destList = ArrayList<Destination>()
    private lateinit var adapter: DestAdapter
    private lateinit var create_btn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_select_list)

        searchView = findViewById(R.id.sv_destination)
        recyclerView = findViewById(R.id.rv_destination)
        create_btn = findViewById(R.id.btn_make_account_book)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        addToList()
        adapter = DestAdapter(destList, R.layout.destination_select_item, this)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        create_btn.setOnClickListener{
            val intent = Intent(this, CreateAccountBook::class.java)
            startActivity(intent)
        }


    }

    private fun filterList(query : String?){
        if(query != null){
            val filteredList = ArrayList<Destination>()
            for(i in destList){
                if(i.name.contains(query)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this, "등록되지 않은 도시입니다.", Toast.LENGTH_SHORT).show()
            }else{
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addToList() {
        destList.add(Destination("bali", R.drawable.bali))
        destList.add(Destination("japan", R.drawable.japan))
        destList.add(Destination("usa", R.drawable.usa))
        destList.add(Destination("philippine", R.drawable.philippine))
        destList.add(Destination("china", R.drawable.china))
        destList.add(Destination("bali", R.drawable.bali))
        destList.add(Destination("japan", R.drawable.japan))

    }

}