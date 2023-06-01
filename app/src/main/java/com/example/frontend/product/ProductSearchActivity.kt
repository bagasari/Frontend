package com.example.frontend.product

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.databinding.ActivityProductSearchBinding
import com.example.frontend.retrofit.RetrofitClient
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class ProductSearchActivity : AppCompatActivity() {
    private val TAG: String = "ProductSearchActivity"
    private lateinit var binding: ActivityProductSearchBinding
    private lateinit var productSearchAdapter: ProductSearchAdapter
    private val searchSubject = PublishSubject.create<String>()
    private var searchDisposable: Disposable? = null
    private val retrofit = RetrofitClient.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // dummy 삭제 예정 - 원래는 비어 있어야 함
        val productList = ProductSearch(emptyList())



        // 품목 리스트
        productSearchAdapter = ProductSearchAdapter(productList = productList)
        binding.productSearchRv.apply {
            layoutManager = LinearLayoutManager(this@ProductSearchActivity)
            adapter = productSearchAdapter
        }

        // 품목 검색창
        binding.productSearchSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
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
        searchDisposable = searchSubject
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .subscribe { query -> performSearch(query) }

    }

    override fun onDestroy() {
        super.onDestroy()
        searchDisposable?.dispose()
    }

    private fun performSearch(query: String){

        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                val response = retrofit.create(ProductService::class.java).getProductSearchAuto(word = query)
                if (response.isSuccessful) {
                    Log.d(TAG, "검색어 자동완성 호출 성공 ${response.body()}")

                    var newProductList = ProductSearch(emptyList())

                    if(response.body() != null)
                        newProductList = response.body()!!

                    withContext(Dispatchers.Main) {
                        productSearchAdapter.setProductList(newProductList)
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }
}