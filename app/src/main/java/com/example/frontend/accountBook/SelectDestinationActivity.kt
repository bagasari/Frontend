package com.example.frontend.accountBook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.adapter.DestAdapter
import com.example.frontend.database.AppDatabase
import com.example.frontend.databinding.ActivityDestinationSelectListBinding
import com.example.frontend.dto.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

// 가계부 생성 시 국가 선택 액티비티
class SelectDestinationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinationSelectListBinding
    private lateinit var searchView: SearchView
    private var destList = ArrayList<Destination>()
    private lateinit var destAdapter: DestAdapter
    private lateinit var destListAdapter: DestListAdapter
    private lateinit var create_btn: Button

    private var db: AppDatabase? = null
    private lateinit var cityList: List<Destination>
    private lateinit var countryList: List<Destination>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // DestinationSelectList 바인딩
        binding = ActivityDestinationSelectListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingDB()

        // searchView, botton 바인딩
        searchView = findViewById(R.id.sv_destination)
        create_btn = findViewById(R.id.btn_make_account_book)

        // 어댑터 초기화
        destAdapter = DestAdapter(destList, R.layout.destination_search_item, this)
        destListAdapter = DestListAdapter()

        // 리사이클러뷰 생성
        binding.rvDestination.layoutManager = LinearLayoutManager(this)
        binding.rvDestination.adapter = destAdapter

        binding.rvDestList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDestList.adapter = destListAdapter

        // 도시 리사이클러뷰 요소 선택 시 -> 하단 리사이클러뷰 생성
        destAdapter.setItemClickListener(object: DestAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int){
                Log.v("test", position.toString())
//                val expenditureAdaptor = ExpenditureAdapter(dateList.get(position).products)
//                binding.rvWritingAccountBook.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                binding.rvWritingAccountBook.adapter = expenditureAdaptor
            }
        })

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
                destAdapter.setFilteredList(filteredList)
            }
        }
    }
    private fun addToList() {
        destList.addAll(countryList)
        destList.addAll(cityList)
        destList[0].img = R.drawable.japan
        destList[1].img = R.drawable.china
        destList[2].img = R.drawable.philippine
        destList[3].img = R.drawable.usa
        destList[4].img = R.drawable.bali
    }

    // [YHJ 4/11] : 국가/도시 DB 초기 세팅 메서드
    private fun settingDB(){
        db = AppDatabase.getInstance(this)
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                countryList = db!!.countryDao().getCountryNameAndImg()
                cityList = db!!.cityDao().getCityNameAndImg()
                addToList()

            }
        }
    }

}