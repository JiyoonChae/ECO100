package com.mapo.eco100.views.ecobox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.databinding.FragmentContentDetailBinding


class ContentDetailFragment : Fragment() {

    private var _binding: FragmentContentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentDetailBinding.inflate(inflater, container, false)

        binding.contentFloatingActionButton.setOnClickListener {
            (activity as ContentDetailActivity).replaceFragment(ContentWebViewFragment.newInstance())


        }
        return binding.root
    }

    companion object {

        fun newInstance(): ContentDetailFragment {
            return ContentDetailFragment()
        }
    }
}