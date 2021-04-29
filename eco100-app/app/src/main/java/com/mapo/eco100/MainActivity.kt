package com.mapo.eco100

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayout
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.navigation.*
import com.mapo.eco100.navigation.HomeViewFragment.Companion.newInstance


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
                binding.appBar.setExpanded(true)
                binding.scrollView.scrollTo(0, 0)
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