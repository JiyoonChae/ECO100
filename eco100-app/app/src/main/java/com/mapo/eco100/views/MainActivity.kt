package com.mapo.eco100.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.EcoBoxViewFragment
import com.mapo.eco100.views.home.HomeViewFragment.Companion.newInstance
import com.mapo.eco100.views.map.MapViewFragment
import com.mapo.eco100.views.myeco.MyEcoViewFragment
import com.mapo.eco100.views.search.SearchActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        supportFragmentManager.beginTransaction()
            .replace(R.id.contents, newInstance()).commit()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                when (tab.position) {
                    0 -> {
                        supportActionBar?.show()
                        ft.replace(R.id.contents, newInstance())
                    }
                    1 -> {
                        supportActionBar?.hide()
                        ft.replace(R.id.contents, EcoBoxViewFragment.newInstance())
                    }
                    2 -> {
                        supportActionBar?.hide()
                        ft.replace(R.id.contents, CommunityViewFragment.newInstance())
                    }
                    3 -> {
                        supportActionBar?.hide()
                        ft.replace(R.id.contents, MapViewFragment.newInstance())
                    }
                    4 -> {
                        supportActionBar?.hide()
                        ft.replace(R.id.contents, MyEcoViewFragment.newInstance())
                    }
                    else -> throw IllegalStateException("Unexpected value: " + tab.position)
                }
                ft.commit()
                binding.appBar.setExpanded(true)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

    /** title menu bar **/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}