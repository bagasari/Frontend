package com.example.frontend.product

import android.Manifest
import android.content.Context
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
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    private lateinit var binding: ActivityProductMapBinding
    private lateinit var googleMap: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productMapMv.onCreate(savedInstanceState)
        binding.productMapMv.getMapAsync(this@ProductMapActivity)
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

        // 위치 권한 확인 및 요청 - 현위치 표시 및 현위치 버튼 활성화
        checkLocationPermission()

        // 현재 위치를 초기 화면으로 설정
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                location?.let {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }


        // 마커 추가 - 더미 삭제 예정
        val drawableResId = R.drawable.ic_marker_food // 리사이징할 이미지의 리소스 ID를 대체해야 합니다.
        val resizedBitmap = resizeBitmap(this, drawableResId, 28, 28)
        val location = LatLng(37.5680, 126.9792)
        val markerOptions = MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))
        val marker = googleMap.addMarker(markerOptions)
        val product: ProductListResponse.Product = ProductListResponse.Product(1,1,"1",1,"1","1","1","1",1)
        val markerList : MutableList<ProductListResponse.Product> = mutableListOf()
        markerList.add(product)
        markerList.add(product)
        markerList.add(product)
        markerList.add(product)
        markerList.add(product)
        markerList.add(product)
        markerList.add(product)

        marker.tag = markerList


        googleMap.setOnMarkerClickListener { clickedMarker ->
            // 마커와 연결된 품목 리스트 데이터 가져오기
            @Suppress("UNCHECKED_CAST")
            val productList = clickedMarker.tag as MutableList<ProductListResponse.Product>

            Log.d("marker", productList.toString())

            // BottomSheet에 품목 리스트 전달
            showBottomSheetMenu(productList = productList)

            true
        }

    }

    // // 마커 클릭시 해당하는 마커에 포함되는 품목 리스트를 띄우는 BottomSheet를 보여주는 메소드
    private fun showBottomSheetMenu(productList: MutableList<ProductListResponse.Product>) {
        val bottomSheetDialog = BottomSheetDialog(this@ProductMapActivity)
        val bottomSheetBinding = FragProductMapBottomSheetBinding.inflate(layoutInflater, null, false)
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // 마커에 해당하는 품목 리스트 리사이클러뷰 어뎁터 생성
        val productMarkerAdapter = ProductMarkerAdapter(productList)

        // 마커에 해당하는 품목 리스트 리사이클러뷰 어뎁터 및 레이아웃 매니저 설정
        bottomSheetBinding.productMapRv.apply {
            layoutManager = LinearLayoutManager(this@ProductMapActivity)
            adapter = productMarkerAdapter
        }

        // 다이얼로그 닫기
        bottomSheetBinding.productMapBtnClose.setOnClickListener{bottomSheetDialog.dismiss()}

        bottomSheetDialog.show()
    }

    // 위치 권한을 확인하는 메소드
    private fun checkLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 허용되지 않은 경우 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(permission), LOCATION_PERMISSION_REQUEST_CODE)
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 현위치 버튼 활성화
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            // 현위치 표시
            googleMap.isMyLocationEnabled = true
        }
    }


    private fun resizeBitmap(context: Context, drawableResId: Int, targetWidth: Int, targetHeight: Int): Bitmap? {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
            BitmapFactory.decodeResource(context.resources, drawableResId, this)
            val imageWidth = outWidth
            val imageHeight = outHeight
            val scaleFactor = calculateScaleFactor(imageWidth, imageHeight, targetWidth, targetHeight)
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }
        return BitmapFactory.decodeResource(context.resources, drawableResId, options)
    }

    private fun calculateScaleFactor(imageWidth: Int, imageHeight: Int, targetWidth: Int, targetHeight: Int): Int {
        var scaleFactor = 1
        if (imageWidth > targetWidth || imageHeight > targetHeight) {
            val widthScaleFactor = imageWidth / targetWidth
            val heightScaleFactor = imageHeight / targetHeight
            scaleFactor = if (widthScaleFactor > heightScaleFactor) widthScaleFactor else heightScaleFactor
        }
        return scaleFactor
    }

}

