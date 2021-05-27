package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityZeroshopDetailBinding

class ZeroshopDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZeroshopDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZeroshopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val detailInfo = intent.getStringExtra("detailInfo")
        val phoneNum = intent.getStringExtra("phoneNum")
        val webUrl = intent.getStringExtra("webUrl")
        val adress = intent.getStringExtra("adress")
        val runInfo = intent.getStringExtra("runInfo")
        val imgUrl = intent.getStringExtra("imgUrl")
        val lat = intent.getDoubleExtra("lat", 0.0)
        val long = intent.getDoubleExtra("long", 0.0)

        supportFragmentManager.beginTransaction()
            .replace(R.id.zeroshop_container, ZeroshopDetailFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putString("name", name)
                        putString("detailInfo", detailInfo)
                        putString("imgUrl",imgUrl)
                        putString("phoneNum", phoneNum)
                        putString("webUrl", webUrl)
                        putString("adress", adress)
                        putString("runInfo", runInfo)
                        putDouble("lat",lat)
                        putDouble("long",long)
                    }
                }
            ).commit()

    }

    fun replaceFragment(fragment: Fragment) {
        val webUrl = intent.getStringExtra("webUrl")
        val name = intent.getStringExtra("name")
        val lat = intent.getDoubleExtra("lat",0.0)
        val long = intent.getDoubleExtra("long",0.0)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(
                R.id.zeroshop_container, fragment
                .apply {
                    arguments = Bundle().apply {
                        putString("webUrl", webUrl)
                        putString("name",name)
                        putDouble("lat",lat)
                        putDouble("long",long)
                    } }
        ).commit()
    }
}