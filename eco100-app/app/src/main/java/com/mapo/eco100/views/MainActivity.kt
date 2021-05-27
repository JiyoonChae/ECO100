package com.mapo.eco100.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.kakao.sdk.common.util.Utility
import com.mapo.eco100.R
import com.mapo.eco100.common.TabItemSelector
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.EcoBoxViewFragment
import com.mapo.eco100.views.home.HomeViewFragment.Companion.newInstance
import com.mapo.eco100.views.login.KakaoLoginUtils
import com.mapo.eco100.views.login.NoLoginDialog
import com.mapo.eco100.views.map.MapViewFragment
import com.mapo.eco100.views.myeco.MyEcoViewFragment


class MainActivity : AppCompatActivity(), TabItemSelector {

    private lateinit var binding: ActivityMainBinding
    private var name : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contents, newInstance()).commit()

        val tabIndex = intent.getIntExtra("tab", -1)
        name = intent.getStringExtra("name")
        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long", 0.0)

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                when (tab.position) {
                    0 -> {
                        ft.replace(R.id.contents, newInstance())
                    }
                    1 -> {
                        ft.replace(R.id.contents, EcoBoxViewFragment.newInstance())
                    }
                    2 -> {
                        var keyHash = Utility.getKeyHash(this@MainActivity)
                        Log.d("keyHash", keyHash)
                        if(!KakaoLoginUtils(this@MainActivity).isLogin()) {
                            val dialog = NoLoginDialog(this@MainActivity)
                            dialog.show()
                        }
                        ft.replace(R.id.contents, CommunityViewFragment.newInstance())
                    }
                    3 -> {
                        if(name == null) {
                            ft.replace(R.id.contents, MapViewFragment.newInstance())
                        }
                    }
                    4 -> {
                        if(!KakaoLoginUtils(this@MainActivity).isLogin()) {
                            val dialog = NoLoginDialog(this@MainActivity)
                            dialog.show()
                        }
                        ft.replace(R.id.contents, MyEcoViewFragment.newInstance())
                    }
                    else -> throw IllegalStateException("Unexpected value: " + tab.position)
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })



        if (tabIndex == 3) {

            val map = MapViewFragment.newInstance()

            var bundle = Bundle()
            bundle.apply {
                putString("name", name)
                putDouble("lat", lat)
                putDouble("long", long)
            }
            map.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.contents, map).commit()
            binding.tab.selectTab(binding.tab.getTabAt(tabIndex))
            name = null
        }

    }

    override fun selectItem(position: Int) {
        binding.tab.selectTab(binding.tab.getTabAt(position))
    }

}