package com.example.frontend.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductSearchBinding
import com.example.frontend.retrofit.RetrofitClient
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class ProductSearchActivity : AppCompatActivity(), ProductSearchAdapter.OnItemClickListener {
    private companion object {
        private const val TAG = "ProductSearchActivity"
    }
    private lateinit var binding: ActivityProductSearchBinding
    private lateinit var productSearchAdapter: ProductSearchAdapter
    private val searchSubject = PublishSubject.create<String>()
    private val searchDisposable = CompositeDisposable()
    private val retrofit = RetrofitClient.getInstance()
    private val productService: ProductService = retrofit.create(ProductService::class.java)
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 품목 리스트
        productSearchAdapter = ProductSearchAdapter(emptyList(), this@ProductSearchActivity)
        binding.productSearchRv.apply {
            layoutManager = LinearLayoutManager(this@ProductSearchActivity)
            adapter = productSearchAdapter
        }

        // 품목 검색창
        binding.productSearchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // 검색어 입력하고 엔터 키 눌렀을 때 호출
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // 검색어가 변경될 때 - 디바운스를 통한 호출
                searchSubject.onNext(newText)
                return true
            }
        })

        // 500ms 동안 입력이 없을 때에만 검색 요청
        searchDisposable.add(
            searchSubject
                .debounce(500, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe { query -> performSearch(query) }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // 검색 작업이 종료되지 않았을 때 종료
        searchDisposable.clear()
        searchJob?.cancel()
    }

    private fun performSearch(query: String) {
        searchJob?.cancel() // 이전 검색 작업을 취소

        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        searchJob = CoroutineScope(Job() + Dispatchers.IO).launch {
            try {
                // 검색어 자동 완성 호출
                val response = productService.getProductSearchAuto(word = query)
                if (response.isSuccessful) {
                    Log.d(TAG, "검색어 자동 완성 호출 성공 ${response.body()}")

                    val newProductList = response.body()?.name.orEmpty()

                    withContext(Dispatchers.Main) {
                        productSearchAdapter.setProductList(newProductList)
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }

    override fun onItemClick(productName: String) {
        Log.d(TAG,"선택한 품목명 :$productName")

        // 선택한 나라/도시
        val destination = intent.getStringExtra("DEST_NAME")
        destination?.let { Log.d(TAG,"선택한 나라/도시 :$it") }

        // 선택한 품목명
        val intent = Intent(this, ProductActivity::class.java)

        // 선택한 품목명, 나라/도시 전달
        intent.putExtra("DEST_NAME",destination)
        intent.putExtra("KEYWORD_NAME", productName)
        startActivity(intent)
    }
}