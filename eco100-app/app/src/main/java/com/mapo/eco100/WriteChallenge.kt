package com.mapo.eco100

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.mapo.eco100.databinding.ActivityWriteChallengeBinding

class WriteChallenge : AppCompatActivity() {
    val binding by lazy { ActivityWriteChallengeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.challengeFinish.setOnClickListener {
            finish()
            val view = layoutInflater.inflate(R.layout.row_challenge, null)
            view.findViewById<ImageView>(R.id.rowStamp1).setImageResource(R.drawable.emoji)
        }

    }
}