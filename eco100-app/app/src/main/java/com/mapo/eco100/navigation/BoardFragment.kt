package com.mapo.eco100.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.eco100.EnrollActivity
import com.mapo.eco100.R
import com.mapo.eco100.config.BOARD_CLICK
import com.mapo.eco100.config.BOARD_ENROLL
import com.mapo.eco100.databinding.FragmentBoardBinding
import com.mapo.eco100.ShowBoardActivity
import com.mapo.eco100.adapter.BoardAdapter
import com.mapo.eco100.config.OkHttpClientObj
import com.mapo.eco100.config.RetrofitObj
import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.viewmodel.BoardViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoardFragment : Fragment() {

    private val viewModel: BoardViewModel by viewModels()

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivityContext: Context
    private lateinit var boardAdapter: BoardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        mainActivityContext = requireContext()

        boardAdapter = BoardAdapter(
            onClickItem = { board_data ->
                val service: BoardService =
                    OkHttpClientObj.retrofit.build().create(BoardService::class.java)
                service.read(board_data.board_id).enqueue(object : Callback<BoardReadForm> {
                    override fun onResponse(
                        call: Call<BoardReadForm>,
                        response: Response<BoardReadForm>
                    ) {
                        val intent = Intent(mainActivityContext, ShowBoardActivity::class.java)
                        intent.putExtra("board_data", response.body()!!)
                        startActivityForResult(intent, BOARD_CLICK)
                    }

                    override fun onFailure(call: Call<BoardReadForm>, t: Throwable) {
                        Toast.makeText(mainActivityContext, "글에 문제가 있습니다.", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                mainActivityContext,
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

        binding.boardWriteButton.setOnClickListener {
            startActivityForResult(
                Intent(mainActivityContext, EnrollActivity::class.java),
                BOARD_ENROLL
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.toolBar.inflateMenu(R.menu.sort_boards)
        binding.toolBar.overflowIcon = ContextCompat.getDrawable(mainActivityContext,R.drawable.ic_baseline_keyboard_arrow_down_24)

        viewModel.apply {
            boardsLiveData.observe(viewLifecycleOwner, Observer {
                boardAdapter.updateBoards(it)
            })

            loadingLiveData.observe(viewLifecycleOwner, Observer { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            })
        }

        binding.toolBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_recent -> {
                    boardAdapter.sortByRecent()
                    binding.sortMenuText.text = "최근 순"
                    true
                }
                R.id.action_likes -> {
                    boardAdapter.sortByLikes()
                    binding.sortMenuText.text = "좋아요 순"
                    true
                }
                else -> false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            BOARD_ENROLL -> {
                Toast.makeText(mainActivityContext, "글 등록 성공", Toast.LENGTH_SHORT).show()
                val responseBoard: Boards = data!!.getSerializableExtra("created_board") as Boards
                boardAdapter.addBoard(responseBoard)
            }
        }
    }

    companion object {
        fun newInstance(): BoardFragment {
            return BoardFragment()
        }
    }

}