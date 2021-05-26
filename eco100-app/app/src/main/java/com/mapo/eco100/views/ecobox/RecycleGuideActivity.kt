package com.mapo.eco100.views.ecobox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityRecycleGuideBinding

class RecycleGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecycleGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecycleGuideBinding.inflate(layoutInflater)

        val number = intent.getIntExtra("number",0)

        changeGuide(number)
        setContentView(binding.root)

    }

    fun changeGuide(num : Int){
        when(num){
            1 -> binding.guideImageView.setImageResource(R.drawable.recycle_guide_paper)
            2-> Glide.with(binding.guideImageView).load(R.drawable.recycle_guide_glass).override(1500,2944).into(binding.guideImageView)
            3 -> Glide.with(binding.guideImageView).load(R.drawable.recycle_guide_plastic).override(1500,2944).into(binding.guideImageView)
            4 -> Glide.with(binding.guideImageView).load(R.drawable.recycle_guide_can).override(1500,2944).into(binding.guideImageView)
            5 -> Glide.with(binding.guideImageView).load(R.drawable.recycle_guide_vinyl).override(1500,2944).into(binding.guideImageView)
            6 -> Glide.with(binding.guideImageView).load(R.drawable.recycle_guide_styrofoam).override(1500,2944).into(binding.guideImageView)

        }

    }

}