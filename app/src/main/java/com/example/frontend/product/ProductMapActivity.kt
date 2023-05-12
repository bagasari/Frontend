package com.example.frontend.product

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityProductMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class ProductMapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityProductMapBinding
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productMapMv.onCreate(savedInstanceState)
        binding.productMapMv.getMapAsync(this)
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
    }
}

