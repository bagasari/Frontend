package com.example.frontend.product

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import com.example.frontend.R
import com.example.frontend.databinding.ActivityProductMapBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityProductMapBinding
    private lateinit var googleMap: GoogleMap
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

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
        val location = LatLng(37.5680, 126.9792)
        val marker = MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_food_1))
        googleMap.addMarker(marker)

    }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 위치 권한이 사용자에 의해 허용된 경우 현위치 버튼 활성화
            enableMyLocation()
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // 현위치 버튼 활성화
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            // 현위치 표시
            googleMap.isMyLocationEnabled = true
        }
    }

}

