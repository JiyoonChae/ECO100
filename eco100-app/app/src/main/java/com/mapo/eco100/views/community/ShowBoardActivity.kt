package com.mapo.eco100.views.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mapo.eco100.adapter.CommentAdapter
import com.mapo.eco100.databinding.ActivityShowBoardBinding
import com.mapo.eco100.entity.board.BoardReadForm



class ShowBoardActivity : AppCompatActivity() {


    private lateinit var commentAdapter: CommentAdapter
    private lateinit var binding: ActivityShowBoardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val board : BoardReadForm = intent.getSerializableExtra("board_data") as BoardReadForm
        board?.let {
            binding.boardTitle.text = it.title
            binding.boardContents.text = it.contents
            it.image_url?.let { url_string ->
                Glide.with(this)
                    .load(url_string.toUri())
                    .override(1440,1440)
                    .into(binding.imageView2)
            }
            binding.boardWriter.text = it.user_nickname
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

        commentAdapter = CommentAdapter()
        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@ShowBoardActivity,
                LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = commentAdapter
        }


    }
}