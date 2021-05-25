package com.mapo.eco100.views.ecobox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityContentDetailBinding

class ContentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContentDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")
        val imgRes = intent.getIntExtra("imgRes", 0)
        val detail = intent.getStringExtra("detail")


        supportFragmentManager.beginTransaction()
            .replace(R.id.detail_content, ContentDetailFragment.newInstance()
                .apply {
                    arguments = Bundle().apply {
                        putString("category", category)
                        putString("title", title)
                        putInt("imgRes", imgRes)
                        putString("detail", detail)
                    }
                }
            ).commit()

    }


    fun replaceFragment(fragment: Fragment) {
        val webUrl = intent.getStringExtra("webUrl")

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(
            R.id.detail_content, fragment
            .apply {
                arguments = Bundle().apply {
                    putString("webUrl", webUrl)
                } }
        ).commit()
    }
}