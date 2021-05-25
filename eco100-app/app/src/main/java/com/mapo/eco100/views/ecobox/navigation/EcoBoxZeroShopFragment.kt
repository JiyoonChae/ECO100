package com.mapo.eco100.views.ecobox.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.config.LocalDataBase
import com.mapo.eco100.databinding.EcoboxFragmentZeroshopBinding

class EcoBoxZeroShopFragment : Fragment() {

    private var _binding: EcoboxFragmentZeroshopBinding? = null
    private val binding get() = _binding!!
    private lateinit var zeroshopRecyclerView: RecyclerView

    var adapter = ZeroshopRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EcoboxFragmentZeroshopBinding.inflate(inflater,container,false)

        zeroshopRecyclerView = binding.ecoboxZeroshopRecyclerview
        zeroshopRecyclerView.adapter = adapter
        zeroshopRecyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter.data = LocalDataBase.zeroShopList

        return binding.root

    }

    companion object {
        fun newInstance(): EcoBoxZeroShopFragment {
            return EcoBoxZeroShopFragment()
        }
    }
}