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
import com.mapo.eco100.views.community.EnrollActivity
import com.mapo.eco100.R
import com.mapo.eco100.config.BOARD_CLICK
import com.mapo.eco100.config.BOARD_ENROLL
import com.mapo.eco100.databinding.FragmentBoardBinding
import com.mapo.eco100.views.community.ShowBoardActivity
import com.mapo.eco100.adapter.BoardAdapter
import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.viewmodel.BoardViewModel
import com.mapo.eco100.MainActivity
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.views.network.NoConnectedDialog
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

        return binding.root
    }
}