package com.mapo.eco100

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.mapo.eco100.databinding.ActivityShowBoardBinding
import com.mapo.eco100.entity.board.Board

class ShowBoardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val board : Board = intent.getSerializableExtra("board_data") as Board
        board?.let {
            binding.boardTitle.text = it.title
            binding.boardContents.text = it.contents
            it.image_url?.let { url_string ->
                Glide.with(this).load(url_string.toUri()).into(binding.imageView2)
            }
            binding.boardWriter.text = it.user_nickname
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}