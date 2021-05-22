package com.mapo.eco100.views.ecobox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
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

        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_content, ContentDetailFragment.newInstance()).commit()


    }

    public fun replaceFragment(fragment: Fragment){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.detail_content, fragment).commit()

    }



}