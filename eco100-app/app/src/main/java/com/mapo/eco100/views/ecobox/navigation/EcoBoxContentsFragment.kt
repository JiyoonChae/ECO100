package com.mapo.eco100.views.ecobox.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentContentsBinding
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding

class EcoBoxContentsFragment : Fragment() {

    private var _binding: EcoboxFragmentContentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentRecyclerView: RecyclerView

    var adapter = ContentRecyclerAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = EcoboxFragmentContentsBinding.inflate(inflater, container, false)

        contentRecyclerView = binding.ecoboxContentRecyclerview
        contentRecyclerView.adapter = adapter
        contentRecyclerView.layoutManager = GridLayoutManager(context,2)


        return binding.root
    }

    companion object {
        fun newInstance(): EcoBoxContentsFragment {
            return EcoBoxContentsFragment()
        }
    }
}