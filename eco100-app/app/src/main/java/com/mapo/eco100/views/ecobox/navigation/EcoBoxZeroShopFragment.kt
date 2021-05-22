package com.mapo.eco100.views.ecobox.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentContentsBinding
import com.mapo.eco100.databinding.EcoboxFragmentZeroshopBinding

class EcoBoxZeroShopFragment : Fragment() {

    private var _binding: EcoboxFragmentZeroshopBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentZeroshopBinding.inflate(inflater,container,false)


        return binding.root

    }

    companion object {
        fun newInstance(): EcoBoxZeroShopFragment {
            return EcoBoxZeroShopFragment()
        }
    }
}