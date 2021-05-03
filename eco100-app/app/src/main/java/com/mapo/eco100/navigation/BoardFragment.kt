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
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.eco100.EnrollActivity
import com.mapo.eco100.config.BOARD_CLICK
import com.mapo.eco100.config.BOARD_ENROLL
import com.mapo.eco100.config.OkHttpClientObj
import com.mapo.eco100.databinding.FragmentBoardBinding
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.ShowBoardActivity
import com.mapo.eco100.adapter.BoardAdapter
import com.mapo.eco100.entity.board.Board
import com.mapo.eco100.viewmodel.BoardViewModel

class BoardFragment : Fragment() {

    private val viewModel: BoardViewModel by viewModels()

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentContext: Context
    private lateinit var boardAdapter : BoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        parentContext = container!!.context

        boardAdapter = BoardAdapter(
            onClickItem = { board ->
                val intent = Intent(parentContext, ShowBoardActivity::class.java)
                intent.putExtra("board_data", board)
                startActivityForResult(intent, BOARD_CLICK)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                parentContext,
                LinearLayoutManager.VERTICAL, false
            )
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    LinearLayoutManager.VERTICAL
                )
            )
            adapter = boardAdapter
        }

        binding.enrollBtn.setOnClickListener {
            startActivityForResult(Intent(parentContext,EnrollActivity::class.java), BOARD_ENROLL)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.apply {
            boardsLiveData.observe(viewLifecycleOwner, Observer {
                boardAdapter.updateBoards(it)
            })

            loadingLiveData.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            })
        }
    }

    /*
    private fun load_boards() {
        Thread {
            val boardService = OkHttpClientObj.retrofit.build().create(BoardService::class.java)
            val response = boardService.boards(0).execute()
            if(response.isSuccessful) {
                Log.d("BoardFragment","데이터 로딩 성공"+response.body()!!)
                boards = response.body()!!
            } else {
                Log.e("BoardFragment","데이터 로딩 실패")
                //Toast.makeText(parentContext,"로딩 실패",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

 */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            BOARD_ENROLL -> {
                Toast.makeText(parentContext, "글 등록 성공", Toast.LENGTH_SHORT).show()
                val responseBoard: Board = data!!.getSerializableExtra("created_board") as Board
                boardAdapter.addBoard(responseBoard)
            }
        }
    }
}