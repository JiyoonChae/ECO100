package com.mapo.eco100.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.EcoBoxViewFragment
import com.mapo.eco100.views.home.HomeViewFragment.Companion.newInstance
import com.mapo.eco100.views.map.MapViewFragment
import com.mapo.eco100.views.myeco.MyEcoViewFragment
import com.mapo.eco100.views.network.NetworkSettings
import com.mapo.eco100.views.network.NoConnectedDialog


class MainActivity : AppCompatActivity() {

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

    }

    fun NoInternetConnectedCallback() {
        if(!NetworkSettings.isNetworkAvailable(this)) {
            Toast.makeText(this,"네트워크가 연결되지 않았습니다.\nWi-Fi또는 데이터를 활성화 해주세요.",Toast.LENGTH_SHORT).show()
            val dialog = NoConnectedDialog(this)
            dialog.show()
        } else {
            Toast.makeText(this,"네트워크가 연결되었습니다.\n다시 시도해 주세요.",Toast.LENGTH_SHORT).show()
        }
    }
}