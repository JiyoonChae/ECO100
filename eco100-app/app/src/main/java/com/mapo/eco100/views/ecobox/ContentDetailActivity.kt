package com.mapo.eco100.views.ecobox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityContentDetailBinding
import com.mapo.eco100.views.home.HomeViewFragment

class ContentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

//
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.contents, HomeViewFragment.newInstance()).commit()
//
//        binding.contentFloatingActionButton.setOnClickListener {
//            val fm: FragmentManager  = supportFragmentManager
//            val ft: FragmentTransaction = fm.beginTransaction()
//
//
//
//        }


    }




}