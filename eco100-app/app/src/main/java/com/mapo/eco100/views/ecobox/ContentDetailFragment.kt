package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R
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

        val imgRes = arguments?.getInt("imgRes")

        arguments?.let {
            binding.contentDetailCategory.text = it.getString("category")
            binding.contentDetailTitle.text = it.getString("title")
            Glide.with(this).load(imgRes).into(binding.contentDetailImageView)
            binding.contentDetailInfo.text = it.getString("detail")

        }
        var categorySt = binding.contentDetailCategory.text.toString()
        when (categorySt) {
            "[다큐]" -> binding.contentDetailCategory.setTextColor(ContextCompat.getColor(container!!.context,R.color.primary_color))
            "[환경툰]" -> binding.contentDetailCategory.setTextColor(ContextCompat.getColor(container!!.context,R.color.content_toon_color))
            "[대외활동]" -> binding.contentDetailCategory.setTextColor(ContextCompat.getColor(container!!.context,R.color.content_activity_color))
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