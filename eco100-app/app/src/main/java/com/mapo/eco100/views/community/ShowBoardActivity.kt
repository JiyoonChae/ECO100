package com.mapo.eco100.views.community

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mapo.eco100.views.login.KakaoLoginUtils
import com.mapo.eco100.R
import com.mapo.eco100.adapter.CommentAdapter
import com.mapo.eco100.config.BOARD_EDIT
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.ActivityShowBoardBinding
import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.comment.CommentRequest
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.service.CommentService
import com.mapo.eco100.viewmodel.ShowBoardViewModel
import com.mapo.eco100.views.network.NoConnectedDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowBoardActivity : AppCompatActivity() {

    private val viewModel: ShowBoardViewModel by viewModels()
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var binding: ActivityShowBoardBinding
    private lateinit var boardData: BoardReadForm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.set(this)

        Glide.with(this@ShowBoardActivity)
            .load(R.drawable.icon_send_btn)
            .into(binding.sendComment)

        boardData = intent.getSerializableExtra("board_data") as BoardReadForm
        if (boardData.userId == getSharedPreferences("login",Context.MODE_PRIVATE).getLong("userId",-1)) {
            setSupportActionBar(binding.editBoardToolBar)//써줘야 onCreateOptionsMenu()가 호출된다
            supportActionBar!!.title = null
        }

        boardData.apply {
            binding.boardTitle.text = title
            binding.boardContents.text = contents
            imageUrl?.let { url_string ->
                Glide.with(this@ShowBoardActivity)
                    .load(url_string.toUri())
                    .override(1440, 1440)
                    .into(binding.imageView2)
            }
            binding.date.text = date
            binding.boardWriter.text = nickname
            binding.numofComments.text = commentsCnt.toString()
            binding.numofLikes.text = likesCnt.toString()
            commentAdapter = CommentAdapter(
                comments,
                onClickDeleteBtn = {comment ->
                    AlertDialog.Builder(this@ShowBoardActivity)
                        .setTitle("댓글을 삭제하시겠습니까?")
                        .setPositiveButton("확인") { _, _ ->
                            val service: CommentService =
                                NetworkSettings.retrofit.build().create(CommentService::class.java)
                            service.delete(comment.commentId).enqueue(object : Callback<Void> {
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                    if(response.isSuccessful) {
                                        viewModel.fetchComments(comment.boardId)
                                        Toast.makeText(
                                            this@ShowBoardActivity,
                                            "댓글을 삭제하였습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {

                                }
                            })
                        }
                        .setNegativeButton("취소") { _, _ -> }
                        .create()
                        .show()
                },
                this@ShowBoardActivity
            )
        }
        Glide.with(this@ShowBoardActivity)
            .load(if(boardData.canClickLikes) R.drawable.icon_likes else R.drawable.icon_likes_pressed)
            .into(binding.sendLikes)

        binding.editBoardToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_edit -> {
                    val intent = Intent(this, EnrollActivity::class.java)
                    intent.putExtra("board_data", boardData)
                    startActivityForResult(intent, BOARD_EDIT)
                    true
                }
                R.id.action_delete -> {
                    deleteBoard()
                    true
                }
                else -> false
            }
        }

        binding.sendLikes.setOnClickListener {
            if (!NetworkSettings.isNetworkAvailable(this)) {
                val dialog = NoConnectedDialog(this)
                dialog.show()
            } else {
                viewModel.fetchNumOfLikes(boardData.boardId)
            }
        }

        binding.sendComment.setOnClickListener {
            if (!NetworkSettings.isNetworkAvailable(this)) {
                val dialog = NoConnectedDialog(this)
                dialog.show()
            } else {
                if (binding.commentEditText.text.toString() != "") {
                    if (!NetworkSettings.isNetworkAvailable(this)) {
                        val dialog = NoConnectedDialog(this)
                        dialog.show()
                    } else if (!KakaoLoginUtils(this).isLogin()) {
                        KakaoLoginUtils(this).login()
                    } else {
                        val userId = this.getSharedPreferences("login", Context.MODE_PRIVATE)
                            .getLong("userId", -1)
                        val service: CommentService =
                            NetworkSettings.retrofit.build().create(CommentService::class.java)
                        service.write(
                            CommentRequest(
                                boardData.boardId,
                                binding.commentEditText.text.toString(),
                                userId
                            )
                        )
                            .enqueue(object : Callback<Void> {
                                override fun onResponse(
                                    call: Call<Void>,
                                    response: Response<Void>
                                ) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(
                                            this@ShowBoardActivity,
                                            "댓글을 등록하였습니다.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        binding.commentEditText.text.clear()

                                        viewModel.fetchComments(boardData.boardId)
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(
                                        this@ShowBoardActivity,
                                        "댓글 전송에 실패하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                    }
                } else {
                    Toast.makeText(this@ShowBoardActivity, "댓글을 입력해 주세요.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.commentsRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                this@ShowBoardActivity,
                LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL))
            adapter = commentAdapter
        }

        viewModel.apply {
            commentsLiveData.observe(this@ShowBoardActivity, {
                commentAdapter.updateComments(it)
                binding.numofComments.text = "${it.size}"
            })

            numOfLikesLiveData.observe(this@ShowBoardActivity, {
                binding.numofLikes.text = "$it"
                boardData.canClickLikes = !boardData.canClickLikes
                Glide.with(this@ShowBoardActivity)
                    .load(if(boardData.canClickLikes) R.drawable.icon_likes else R.drawable.icon_likes_pressed)
                    .into(binding.sendLikes)
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.edit_board, menu)
        return true
    }

    fun deleteBoard() {
        val service = NetworkSettings.retrofit.build().create(BoardService::class.java)
        service.delete(boardData.boardId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(this@ShowBoardActivity, "글이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@ShowBoardActivity, "글을 삭제하지 못했습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            BOARD_EDIT -> {

            }
        }
    }
}