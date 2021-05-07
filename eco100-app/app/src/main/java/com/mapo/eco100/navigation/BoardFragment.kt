package com.mapo.eco100.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mapo.eco100.EnrollActivity
import com.mapo.eco100.config.BOARD_CLICK
import com.mapo.eco100.config.BOARD_ENROLL
import com.mapo.eco100.databinding.FragmentBoardBinding
import com.mapo.eco100.ShowBoardActivity
import com.mapo.eco100.adapter.BoardAdapter
import com.mapo.eco100.entity.board.Board
import com.mapo.eco100.viewmodel.BoardViewModel

class BoardFragment : Fragment() {

    private val viewModel: BoardViewModel by viewModels()

    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainActivityContext: Context
    private lateinit var boardAdapter : BoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBoardBinding.inflate(inflater, container, false)
        mainActivityContext = requireContext()

        boardAdapter = BoardAdapter(
            onClickItem = { board ->
                val intent = Intent(mainActivityContext, ShowBoardActivity::class.java)
                intent.putExtra("board_data", board)
                startActivityForResult(intent, BOARD_CLICK)
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

        binding.enrollBtn.setOnClickListener {
            startActivityForResult(Intent(mainActivityContext,EnrollActivity::class.java), BOARD_ENROLL)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            BOARD_ENROLL -> {
                Toast.makeText(mainActivityContext, "글 등록 성공", Toast.LENGTH_SHORT).show()
                val responseBoard: Board = data!!.getSerializableExtra("created_board") as Board
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