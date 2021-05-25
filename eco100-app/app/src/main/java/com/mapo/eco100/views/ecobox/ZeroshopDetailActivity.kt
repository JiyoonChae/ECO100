package com.mapo.eco100.views.ecobox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import com.bumptech.glide.Glide
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


        supportFragmentManager.beginTransaction()
            .replace(R.id.zeroshop_container, ZeroshopDetailFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putString("name", name)
                        putString("detailInfo", detailInfo)
                        putString("phoneNum", phoneNum)
                        putString("webUrl", webUrl)
                        putString("adress", adress)
                        putString("runInfo", runInfo)
                    }
                }
            ).commit()

    }

    fun replaceFragment(fragment: Fragment) {
        val webUrl = intent.getStringExtra("webUrl")

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(
            R.id.zeroshop_container, fragment
                .apply {
                    arguments = Bundle().apply {
                        putString("webUrl", webUrl)
                    } }
        ).commit()
    }
}