package com.mapo.eco100.views.ecobox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityContentDetailBinding
import com.mapo.eco100.entity.staticmodel.Contents
import com.mapo.eco100.views.home.HomeViewFragment

class ContentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentDetailBinding

    val category = intent.getStringExtra("category")
    val title = intent.getStringExtra("title")
    val imgRes = intent.getIntExtra("imgRes", 0)
    val detail = intent.getStringExtra("detail")
    val webUrl = intent.getStringExtra("webUrl")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_content, ContentDetailFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putString("category", category)
                        putString("title", title)
                        putInt("imgRes", imgRes)
                        putString("detail", detail)
                        putString("webUrl", webUrl)
                    }
                }
            ).commit()

    }

    fun replaceFragment(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.detail_content, fragment
            .apply {
                arguments = Bundle().apply {
                    putString("webUrl", webUrl)
                }
            }).commit()
    }
}