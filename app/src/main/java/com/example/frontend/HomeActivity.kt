package com.example.frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.frontend.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var mBinding: ActivityHomeBinding? = null
    private val binding get() = mBinding!!

    private val homeFragment by lazy {HomeFragment()}
    private val searchFragment by lazy {SearchFragment()}
    private val myAccountBookListFragment by lazy {MyAccountBookListFragment()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_fragment_test)

        mBinding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initNavigationBar()


    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.
            beginTransaction().
            replace(R.id.container, fragment).
            commit()
    }

    private fun initNavigationBar() {

        binding.navView.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.item_home -> {
                        changeFragment(homeFragment)
                    }
                    R.id.item_search -> {
                        changeFragment(searchFragment)
                    }
                    R.id.item_account_book -> {
                        changeFragment(myAccountBookListFragment)
                    }

                }
                true
            }
            selectedItemId = R.id.item_home
        }
    }

}