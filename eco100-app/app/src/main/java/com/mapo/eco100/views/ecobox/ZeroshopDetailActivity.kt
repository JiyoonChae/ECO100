package com.mapo.eco100.views.ecobox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mapo.eco100.databinding.ActivityZeroshopDetailBinding

class ZeroshopDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZeroshopDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityZeroshopDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}