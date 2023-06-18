package com.example.frontend.expenditure

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentManager
import com.example.frontend.R
import com.example.frontend.database.AppDatabase
import com.example.frontend.database.CityNames
import com.example.frontend.databinding.ActivityFoodFormBinding
import com.example.frontend.dto.Destination
import com.example.frontend.home.HomeActivity
import com.example.frontend.retrofit.RetrofitClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.rxjava3.internal.operators.flowable.FlowableTakeLastTimed
import kotlinx.coroutines.*
import org.json.JSONObject
import java.util.*

class FoodFormActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityFoodFormBinding
    private lateinit var mapFragment: SupportMapFragment
    private lateinit var googleMap: GoogleMap
    private var clickedMarker: Marker? = null

    private lateinit var productCountry: String
    private lateinit var productCity: String
    private lateinit var latitude: String
    private lateinit var longitude: String

    // retrofit 통신
    private val retrofit = RetrofitClient.getInstance()

    // DB
    private var db: AppDatabase? = null
    private var destList = listOf<CityNames>()
    private lateinit var cityList: List<CityNames>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        settingDB()

        val accountBookId = intent.getStringExtra("AccountBookId")!!.toLong()
        val productName = intent.getStringExtra("productName")!!
        val productPrice = intent.getStringExtra("productPrice")!!
        val productDetail = intent.getStringExtra("productDetail")!!
        val purchaseDate = intent.getStringExtra("purchaseDate")!!

        //food 입력 폼

        Log.e("Food", productName + productPrice + productDetail + purchaseDate)


        // 구글맵 지도에서 좌표 불러오기
        // MapFragment 초기화
        mapFragment = supportFragmentManager.findFragmentById(R.id.fg_google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        binding.btnSubmit.setOnClickListener{
            val foodCountText = binding.etFoodCount.text.toString()
            val foodWeightText = binding.etFoodWeight.text.toString()

            if (foodCountText.isNotEmpty() && foodWeightText.isNotEmpty() && productPrice.isNotEmpty() && productCountry.isNotEmpty() && productCity.isNotEmpty() && !productCity.equals("해당 도시는 지원되지 않습니다.") && longitude.isNotEmpty() && latitude.isNotEmpty()) {
                val foodCount = foodCountText.toInt()
                val foodWeight = foodWeightText.toInt()
                val product: PostProduct = PostProduct(accountBookId, productName, productPrice.toInt(), purchaseDate, productDetail, productCountry, productCity)

                val food: Food = Food(foodCount, foodWeight, latitude, longitude)
                val expenditureFoodDTO: ExpenditureFoodDTO = ExpenditureFoodDTO(product, food)

                val intent = Intent(this, ExpenditureActivity::class.java).apply {
                    putExtra("userId", "still")
                    putExtra("FRAG_NUM", "accountBook")
                }

                postFoodExpenditure(expenditureFoodDTO)
                finish()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        // GoogleMap 객체가 사용 가능할 때 호출됩니다.
        googleMap = map

        // 여기에서 필요한 구글 맵 설정 및 조작을 수행합니다.
        // 예: 지도 유형 설정, 마커 추가 등

        // onMapReady() 콜백 메서드에서 다음 코드를 추가합니다.
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        googleMap.isMyLocationEnabled = true

        // onMapReady() 콜백 메서드에서 다음 코드를 추가합니다.
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                // 사용자의 최근 위치를 가져옴
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                }
            }

        // GoogleMap 객체를 가져온 후, 다음 코드를 추가합니다.
        val mapClickListener = MapClickListener()
        googleMap.setOnMapClickListener(mapClickListener)

    }

    private fun postFoodExpenditure(expenditureFoodDTO: ExpenditureFoodDTO){

        // I/O 작업을 비동기적으로 처리하기 위한 코루틴 스코프를 생성
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        scope.launch {
            try {
                // 로그인 요청
                // val post: PostAccountBookDTO = PostAccountBookDTO("","","",true,postAccountBook.cityList)
                val response = retrofit.create(ExpenditureService::class.java).createFoodExpenditure(expenditureFoodDTO)
                if (response.isSuccessful) {
                    Log.d("createExpenditure", "지출내역 전송 성공 ${response.body()}")
                    val isokay = response.body()
                    Log.d("createExpenditure", isokay!!)


                } else {
                    val errorBody = JSONObject(response.errorBody()?.string() ?: "")
                    val errorCode = errorBody.optString("code")
                    Log.d("createExpenditure", "지출내역 전송 실패 $errorBody")
                    withContext(Dispatchers.Main){
                        when (errorCode) {
                            "C001" -> Log.d("createExpenditure", "존재하지 않는 가계부")
                            "C002" -> Log.d("createExpenditure", "가계부 존재")
                            else -> {
                                Log.d("createExpenditure", "모르겠음")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("createExpenditure", "API 호출 실패 $e")
            }
        }
    }

    private fun settingDB(){
        db = AppDatabase.getInstance(this)
        CoroutineScope(Dispatchers.Main).launch {
            async(Dispatchers.IO){
                cityList = db!!.cityDao().getCityNameEngAndKOR()
                destList = cityList
            }
        }
    }

    inner class MapClickListener : GoogleMap.OnMapClickListener {

        override fun onMapClick(latLng: LatLng) {
            // 클릭한 위치의 좌표를 사용하여 원하는 작업을 수행합니다.
            latitude = latLng.latitude.toString()
            longitude = latLng.longitude.toString()
            // 클릭한 위치의 좌표를 사용하여 Reverse Geocoding을 수행합니다.
            val geocoder = Geocoder(this@FoodFormActivity, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)!!

            if (addresses.isNotEmpty()) {
                val address = addresses?.get(0)
                val country = address?.countryName // 국가명
                val city = address?.adminArea // 도시명

                // 국가와 도시 정보를 출력하거나 필요한 작업을 수행합니다.

                productCountry = country.toString()
                productCity = transformEtoK(city.toString())

                Log.d("MapClickListener", "클릭한 위치의 국가: $productCountry")
                Log.d("MapClickListener", "클릭한 위치의 도시: $productCity, lowercase : ${city.toString()}")
            }

            Log.d("MapClickListener", "latitude : " + latitude.toString() + "longitude : " + longitude.toString())
            clickedMarker?.remove()

            clickedMarker = googleMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_marker_food)))

            )

        }

        private fun transformEtoK(cityName: String): String {

            Log.d("MapClickListener", destList.get(4).name + destList.get(4).name_eng + cityName)

            val koCityName = destList?.find{ it.name_eng.equals(cityName)}
            if(koCityName != null){
                return koCityName.name
            }else{
                return "해당 도시는 지원되지 않습니다."
            }

        }


    }

}


