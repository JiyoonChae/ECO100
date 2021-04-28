package com.mapo.eco100.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.eco100.config.BOARD_CLICK
import com.mapo.eco100.config.BOARD_ENROLL
import com.mapo.eco100.config.OkHttpClientObj
import com.mapo.eco100.databinding.FragmentBoardBinding
import com.mapo.eco100.service.BoardService
import com.sg.eco100.activity.ShowBoardActivity
import com.sg.eco100.adapter.BoardAdapter
import com.sg.eco100.entity.board.Board
import com.sg.eco100.viewmodel.BoardViewModel

class BoardFragment : Fragment() {
    private lateinit var boards : ArrayList<Board>
    private val viewModel : BoardViewModel by lazy {
        BoardViewModel(boards)
    }
    private var _binding : FragmentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater,container,false)
        parentContext = container!!.context

        boards = ArrayList()
        load_boards()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(parentContext,
                LinearLayoutManager.VERTICAL,false)
            addItemDecoration(
                DividerItemDecoration(context,
                    LinearLayoutManager.VERTICAL)
            )
            adapter = BoardAdapter(
                boards,
                onClickItem = { board ->
                    val intent = Intent(parentContext, ShowBoardActivity::class.java)
                    intent.putExtra("board_data",board)
                    startActivityForResult(intent, BOARD_CLICK)
                }
            )
        }

        return binding.root
    }

    private fun load_boards() {
        Thread {
            val boardService = OkHttpClientObj.retrofit.build().create(BoardService::class.java)
            val response = boardService.boards(0).execute()
            if(response.isSuccessful) {
                Log.d("BoardFragment","데이터 로딩 성공"+response.body()!!)
                boards = response.body()!!
                viewModel.refreshBoards(boards)
                viewModel.recentBoards.observe(this, Observer {recentBoards->
                    (binding.recyclerView.adapter as BoardAdapter).setData(recentBoards)
                })
            } else {
                Log.e("BoardFragment","데이터 로딩 실패")
                //Toast.makeText(parentContext,"로딩 실패",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            BOARD_ENROLL -> {
                Toast.makeText(parentContext,"글 등록 성공", Toast.LENGTH_SHORT).show()
                val responseBoard : Board = data!!.getSerializableExtra("created_board") as Board
                viewModel.refreshBoards(responseBoard)
                viewModel.recentBoards.observe(this, Observer {recentBoards->
                    (binding.recyclerView.adapter as BoardAdapter).setData(recentBoards)
                })
            }
        }
    }
}