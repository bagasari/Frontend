package com.example.frontend.product

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend.R
import com.example.frontend.databinding.ActivityProductMapBinding
import com.example.frontend.databinding.FragProductMapBottomSheetBinding
import com.example.frontend.retrofit.RetrofitClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductMapDynamicActivity : AppCompatActivity(), OnMapReadyCallback, ProductMarkerAdapter.OnItemClickListener {
    companion object{
        private const val TAG = "ProductMapActivity"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private val retrofit = RetrofitClient.getInstance()
    private lateinit var binding: ActivityProductMapBinding

    private var productId: Long = -1 // 선택한 품목 ID
    private var productName: String? = null // 선택한 품목 이름

    private lateinit var googleMap: GoogleMap

    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val context:Context = this
        sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)

        // 선택한 품목 ID
        productId = intent.getLongExtra("PRODUCT_ID",-1)
        Log.d(TAG, "선택한 품목 ID :$productId")

        // 선택한 품목명
        productName = intent.getStringExtra("PRODUCT_NAME")
        Log.d(TAG, "선택한 품목명 :$productName")

        binding.productMapMv.onCreate(savedInstanceState)
        binding.productMapMv.getMapAsync(this@ProductMapDynamicActivity)
    }


    override fun onResume() {
        super.onResume()
        binding.productMapMv.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.productMapMv.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.productMapMv.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.productMapMv.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // 지도 사용 가능한 상태
        // 필요한 경우 여기에서 지도에 마커, 선, 옵션 등을 추가하거나 다른 지도 조작을 수행할 수 있습니다.

        // 확대/축소 버튼 활성화
        googleMap.uiSettings.isZoomControlsEnabled = true

        // 최대 확대 레벨 설정
        map.setMaxZoomPreference(15f)

        // 최소 축소 레벨 설정
        map.setMinZoomPreference(7f)

        // 위치 권한 확인 및 요청 - 현위치 표시 및 현위치 버튼 활성화
        checkLocationPermission()

        // 마커 추가
        getProductMarkersDynamic()

        // 마커 클릭 리스너 등록
        googleMap.setOnMarkerClickListener { clickedMarker ->
            // 마커와 연결된 품목 리스트 데이터 가져오기
            @Suppress("UNCHECKED_CAST")
            val productList = clickedMarker.tag as MutableList<ProductMarkerResponse>

            Log.d("marker", productList.toString())

            // BottomSheet에 품목 리스트 전달
            showBottomSheetMenu(productList = productList)

            true
        }

    }

    // 품목 마커 리스트 가져오기
    private fun getProductMarkersDynamic(){
        var isInit = false

        // 비동기 작업 시작
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 데이터를 가져오는 API 호출
                val response = retrofit.create(ProductService::class.java).getProductMarkersDynamic(
                    ProductMarkerDynamicRequest(id = productId, name = productName)
                )

                if (response.isSuccessful) {
                    Log.d(TAG, "품목 마커 리스트 ${response.body()}")

                    val markerList = response.body()

                    // 이중 리스트
                    if (markerList != null) {
                        for (list in markerList) {
                            if (list.isNotEmpty()) {
                                // 마커 목록이 있을 경우 맨 처음 인덱스를 대표 마커로 설정
                                val markerData = list[0]

                                withContext(Dispatchers.Main) {
                                    if(!isInit){
                                        // 마커 목록의 첫번째 품목을 초기화면으로 설정
                                        val initLatLng = LatLng(markerData.food.latitude, markerData.food.longitude)
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, 15f))
                                        isInit = true
                                    }

                                    val drawableResId = R.drawable.ic_marker_food // 리사이징할 이미지의 리소스 ID를 대체해야 합니다.
                                    val resizedBitmap = resizeBitmap(this@ProductMapDynamicActivity, drawableResId, 28, 28)
                                    val location = LatLng(markerData.food.latitude, markerData.food.longitude)
                                    val markerOptions = MarkerOptions()
                                        .position(location)
                                        .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))
                                    val marker = googleMap.addMarker(markerOptions)
                                    marker.tag = list
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }


    // // 마커 클릭시 해당하는 마커에 포함되는 품목 리스트를 띄우는 BottomSheet를 보여주는 메소드
    private fun showBottomSheetMenu(productList: MutableList<ProductMarkerResponse>) {
        val bottomSheetDialog = BottomSheetDialog(this@ProductMapDynamicActivity)
        val bottomSheetBinding = FragProductMapBottomSheetBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // 마커에 해당하는 품목 리스트 리사이클러뷰 어뎁터 생성
        val productMarkerAdapter = ProductMarkerAdapter(this@ProductMapDynamicActivity, productList,this@ProductMapDynamicActivity)

        // 마커에 해당하는 품목 리스트 리사이클러뷰 어뎁터 및 레이아웃 매니저 설정
        bottomSheetBinding.productMapRv.apply {
            layoutManager = LinearLayoutManager(this@ProductMapDynamicActivity)
            adapter = productMarkerAdapter
        }

        // 다이얼로그 닫기
        bottomSheetBinding.productMapBtnClose.setOnClickListener{bottomSheetDialog.dismiss()}

        bottomSheetDialog.show()
    }

    // 위치 권한을 확인하는 메소드
    private fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this@ProductMapDynamicActivity, permission) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 허용되지 않은 경우 권한 요청
            ActivityCompat.requestPermissions(this@ProductMapDynamicActivity, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            // 위치 권한이 이미 허용된 경우 현위치 버튼 활성화
            enableMyLocation()
        }
    }

    // 위치 권한이 허용되지 않은 경우 권한 요청하는 메소드
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 사용자에 의해 허용된 경우 현위치 버튼 활성화
            enableMyLocation()
        }
    }

    // 현위치 버튼 활성화 메소드
    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this@ProductMapDynamicActivity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 현위치 버튼 활성화
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            // 현위치 표시
            googleMap.isMyLocationEnabled = true
        }
    }

    // 이미지를 지정한 크기의 Bitmap으로 변경하는 메소드
    private fun resizeBitmap(context: Context, drawableResId: Int, targetWidth: Int, targetHeight: Int): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(context.resources, drawableResId, this@apply)
            val imageWidth = outWidth
            val imageHeight = outHeight
            val scaleFactor = calculateScaleFactor(imageWidth, imageHeight, targetWidth, targetHeight)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        return BitmapFactory.decodeResource(context.resources, drawableResId, options)
    }

    // 이미지의 크기를 계산하는 메소드
    private fun calculateScaleFactor(imageWidth: Int, imageHeight: Int, targetWidth: Int, targetHeight: Int): Int {
        var scaleFactor = 1
        if (imageWidth > targetWidth || imageHeight > targetHeight) {
            val widthScaleFactor = imageWidth / targetWidth
            val heightScaleFactor = imageHeight / targetHeight
            scaleFactor = if (widthScaleFactor > heightScaleFactor) widthScaleFactor else heightScaleFactor
        }
        return scaleFactor
    }

    override fun onItemClick(product: ProductMarkerResponse) {
        val isLike = product.isLike
        val productId = product.product.id
        val email = sharedPref.getString("EMAIL", "")

        if(isLike){
            // 좋아요 눌린 상태 -> 좋아요 취소
            postProductDisLike(email!!, productId)
        }
        else{
            // 좋아요 안눌린 상태 -> 좋아요
            postProductLike(email!!, productId)
        }

    }

    private fun postProductLike(email: String, productId: Long){
        // 비동기 작업 시작
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 데이터를 가져오는 API 호출
                val response = retrofit.create(ProductService::class.java).postProductLike(
                    ProductLikeRequest(productId = productId)
                )

                if (response.isSuccessful) {
                    Log.d(TAG, "품목 좋아요 성공 ${response.body()}")
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }

    private fun postProductDisLike(email: String, productId: Long){
        // 비동기 작업 시작
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // 데이터를 가져오는 API 호출
                val response = retrofit.create(ProductService::class.java).postProductDisLike(
                    ProductLikeRequest(productId = productId)
                )

                if (response.isSuccessful) {
                    Log.d(TAG, "품목 좋아요 성공 ${response.body()}")
                }
            } catch (e: Exception) {
                Log.d(TAG, "API 호출 실패 $e")
            }
        }
    }
}

