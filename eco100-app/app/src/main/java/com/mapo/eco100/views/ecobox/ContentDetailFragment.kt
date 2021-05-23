package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.mapo.eco100.databinding.FragmentContentDetailBinding
import com.mapo.eco100.views.ecobox.navigation.ContentRecyclerAdapter


class ContentDetailFragment : Fragment() {

    private var _binding: FragmentContentDetailBinding? = null
    private val binding get() = _binding!!
    var adapter = ContentRecyclerAdapter()

    private  var category: String = ""
    private var title: String = ""
    private var imgRes: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContentDetailBinding.inflate(inflater, container, false)


        arguments?.let {
            binding.contentDetailCategory.text = it.getString("category")
            binding.contentDetailTitle.text = it.getString("title")
            binding.contentDetailImageView.setImageResource(it.getInt("imgRes"))
            binding.contentDetailInfo.text = it.getString("detail")

        }

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