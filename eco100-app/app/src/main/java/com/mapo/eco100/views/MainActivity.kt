package com.mapo.eco100.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.mapo.eco100.R
import com.mapo.eco100.common.TabItemSelector
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.EcoBoxViewFragment
import com.mapo.eco100.views.home.HomeViewFragment.Companion.newInstance
import com.mapo.eco100.views.map.MapViewFragment
import com.mapo.eco100.views.myeco.MyEcoViewFragment


class MainActivity : AppCompatActivity(), TabItemSelector {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contents, newInstance()).commit()


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
                        ft.replace(R.id.contents, CommunityViewFragment.newInstance())
                    }
                    3 -> {
                        ft.replace(R.id.contents, MapViewFragment.newInstance())
                    }
                    4 -> {
                        ft.replace(R.id.contents, MyEcoViewFragment.newInstance())
                    }
                    else -> throw IllegalStateException("Unexpected value: " + tab.position)
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        val tabIndex = intent.getIntExtra("tab",-1)
        val name = intent.getStringExtra("name")
        val lat = intent.getDoubleExtra("lat",0.0)
        val long = intent.getDoubleExtra("long",0.0)

        if(tabIndex == 3){

            var fragment = MapViewFragment()
            var bundle = Bundle()
            bundle.apply {
                putString("name",name)
                putDouble("lat",lat)
                putDouble("long",long)
            }
            fragment.arguments=bundle
            binding.tab.getTabAt(tabIndex)?.select()
        }

    }

    override fun selectItem(position: Int) {
        binding.tab.selectTab(binding.tab.getTabAt(position))
    }

}