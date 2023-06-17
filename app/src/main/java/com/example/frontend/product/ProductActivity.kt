package com.example.frontend.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.databinding.ActivityProductBinding
import com.example.frontend.databinding.FragProductBottomSheetBinding
import com.example.frontend.retrofit.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.*
import java.util.*

// 상품 검색 액티비티
class ProductActivity: AppCompatActivity(), ProductAdapter.OnItemClickListener {
    private companion object{
        private const val TAG = "ProductActivity"
    }
    private lateinit var binding: ActivityProductBinding
    private val retrofit = RetrofitClient.getInstance()

    private var keyword: String? = null // 검색 키워드
    private var destination: String = "" // 검색 도시/국가
    private var sort = "id" // 정렬 방법 - 기본값 (최신순)

    private var isLatestSelected = true // 최신순, 추천순 선택
    private var productList = mutableListOf<ProductListResponse.Product>()// 품목 리스트
    private lateinit var productAdapter: ProductAdapter // 품목 리스트 리사이클러뷰 어뎁터
    private var isLoading = false // 데이터 로딩 중 여부를 나타내는 변수
    private var isLastPage = false // 모든 데이터를 로드했는지 여부를 나타내는 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색한 나라/도시
        destination = intent.getStringExtra("DEST_NAME").toString()
        Log.d(TAG, "선택한 나라/도시 :$destination")

        // 검색한 품목 키워드
        keyword = intent.getStringExtra("KEYWORD_NAME")
        Log.d(TAG, "선택한 품목명 :$keyword")

        /** 더미데이터 테스트 - 삭제예정 **/
        destination = "도쿄"

        // 검색한 나라/도시 텍스트 설정
        binding.productBtnSearchCity.text = destination

        // 검색한 품목 키워드가 있을 경우 품목 검색창에 텍스트 설정
        if(keyword != null)
            binding.productBtnSearchProduct.text = keyword

        // 품목 리스트 리사이클러뷰 어뎁터 생성
        productAdapter = ProductAdapter(productList, this@ProductActivity)

        // 품목 리스트 리사이클러뷰 어뎁터 및 레이아웃 매니저 설정
        binding.productRv.apply {
            layoutManager = LinearLayoutManager(this@ProductActivity)
            adapter = productAdapter
        }

        // 품목 리스트 리사이클러뷰 초기값 설정
        initProductList(keyword = keyword, location = destination, sort = sort)

        // 검색한 나라/도시 텍스트 설정 클릭 리스너 설정
        binding.productBtnSearchCity.setOnClickListener { finish() }

        // 지도 버튼 클릭 리스너 설정
        binding.productBtnMap.setOnClickListener {
            val intent = Intent(this@ProductActivity, ProductMapStaticActivity::class.java)
            intent.putExtra("DEST_NAME",destination)
            intent.putExtra("KEYWORD_NAME",keyword)
            startActivity(intent)
        }

        // 품목 검색창 클릭 리스너 설정
        binding.productBtnSearchProduct.setOnClickListener {
            val intent = Intent(this@ProductActivity, ProductSearchActivity::class.java)
            intent.putExtra("DEST_NAME",destination)
            startActivity(intent)
            finish()
        }

        // 품목 날짜 선택 클릭 리스너 설정
        binding.productBtnDate.setOnClickListener{
               showDateRangePickerDialog{ startDate, endDate ->
                   val date = "$startDate ~ $endDate"
                   binding.productBtnDate.text = date
               }
        }

        // 필터 버튼 클릭리스너 설정
        binding.productBtnFilter.setOnClickListener{showBottomSheetMenu()}

        // 품목 리스트 리사이클러뷰 스크롤 리스너 설정
        binding.productRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // 현재 리사이클러뷰의 레이아웃 매니저
                val layoutManager = recyclerView.layoutManager

                // 현재 화면에 보이는 아이템 개수
                val visibleItemCount = layoutManager?.childCount ?: 0

                // 전체적으로 등록된 아이템 개수
                val totalItemCount = layoutManager?.itemCount ?: 0

                // 현재 화면에 보이는 첫번째 아이템의 위치
                val firstVisibleItemPosition = (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition() ?: 0

                // 마지막 아이템이 현재 화면에 보이는지 여부 - 전체적으로 등록된 아이템이 다 보여졌는지 확인
                val isLastItemVisible = (visibleItemCount + firstVisibleItemPosition) >= totalItemCount

                // 현재 로딩중이지 않고, 서버에서 모든 데이터를 로드하지 않았을때
               val isNotLoadingAndNotLastPage = !isLoading && !isLastPage

                if (isLastItemVisible && isNotLoadingAndNotLastPage) {
                    // 끝에 도달했으므로 추가 데이터를 로드하는 작업을 수행합니다.
                    loadProductList(keyword = keyword, location = destination, sort = sort, lastId = productList[productList.size-1].id)
                }
            }
        })

    }

    override fun onItemClick(productId: Long, productName: String) {
        val intent = Intent(this@ProductActivity, ProductMapDynamicActivity::class.java)
        intent.putExtra("PRODUCT_ID", productId)
        intent.putExtra("PRODUCT_NAME", productName)
        startActivity(intent)
    }

    // 품목 리스트의 초기값을 가져오는 메소드
    private fun initProductList(keyword: String?, location: String, sort: String){
        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 검색 keyword - null 가능
                // location - not null
                // 정렬 기준 초기값 - id (최신순)
                // lastID 초기값 - 아직 품목을 가져오지 않은 상태 - lastId =  null
                val response = retrofit.create(ProductService::class.java).getProductList(keyword = keyword, location = location, sort = sort, lastId = null)
                if (response.isSuccessful) {
                    Log.d(TAG, "품목 리스트 초기값 호출 성공 ${response.body()}")

                    val initProductList = response.body()?.content.orEmpty()

                    withContext(Dispatchers.Main) {
                        productList.addAll(initProductList)
                        productAdapter.notifyDataSetChanged()
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }

    // 품목 리스트의 끝에 도달했을 때 추가적인 품목 리스트를 가져오는 메소드
    private fun loadProductList(keyword: String?, location: String, sort: String, lastId: Long) {
        isLoading = true // 데이터 로딩 중 상태로 설정

        // 비동기 작업 시작
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 데이터를 가져오는 API 호출
                val response = retrofit.create(ProductService::class.java).getProductList(keyword = keyword, location = location, sort = sort, lastId = lastId)

                if (response.isSuccessful) {
                    Log.d(TAG, "추가적인 품목 리스트 호출 성공 ${response.body()}")

                    val newProductList = response.body()?.content.orEmpty()

                    withContext(Dispatchers.Main) {
                        productList.addAll(newProductList)
                        productAdapter.notifyDataSetChanged()
                        isLoading = false
                        isLastPage = newProductList.isEmpty()
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }

    // 최신순, 추천순을 고를 수 있는 BottomSheet를 보여주는 메소드
    private fun showBottomSheetMenu() {
        val bottomSheetDialog = BottomSheetDialog(this@ProductActivity)
        val bottomSheetBinding = FragProductBottomSheetBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // Bottom Sheet 내부의 View 이벤트 처리 - 선택했던 곳에 체크 표시
        bottomSheetBinding.productBtnLatest.isSelected = isLatestSelected
        bottomSheetBinding.productBtnRecommend.isSelected = !isLatestSelected

        bottomSheetBinding.productBtnLatest.setOnClickListener {
            // 최신순을 선택한 경우 처리
            isLatestSelected = !isLatestSelected
            // 정렬 방법 - 최신순
            sort = "id"
            // 품목 리스트 초기화
            productList = mutableListOf()
            productAdapter.notifyDataSetChanged()
            // 품목 리스트의 초기값 불러옴
            initProductList(keyword = keyword, location = destination, sort = sort)
            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.productBtnRecommend.setOnClickListener {
            // 추천순을 선택한 경우 처리
            isLatestSelected = !isLatestSelected
            // 정렬 방법 - 추천순
            sort = "like"
            // 품목 리스트 초기화
            productList = mutableListOf()
            productAdapter.notifyDataSetChanged()
            // 품목 리스트의 초기값 불러옴
            initProductList(keyword = keyword, location = destination, sort = sort)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }

    // 날짜를 선택하는 DatePickerDialog를 보여주는 메소드
    private fun showDateRangePickerDialog(onDateRangeSet: (String, String) -> Unit) {

        val datePickerDialog = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("날짜를 선택해주세요.")
            .setSelection(
                androidx.core.util.Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()

        datePickerDialog.addOnPositiveButtonClickListener { dateRange ->
            // 선택한 기간 처리
            val startDate = convertDateToString(dateRange.first)
            val endDate = convertDateToString(dateRange.second)
            onDateRangeSet(startDate, endDate)
        }

        datePickerDialog.show(supportFragmentManager, "DateRangePickerDialog")
    }

    // YYYY-MM-DD 로 날짜의 포맷을 변경하는 메소드
    private fun convertDateToString(date: Long): String {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = date
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return String.format("%04d-%02d-%02d", year, month, day)
    }
}