package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentContentDetailBinding
import com.mapo.eco100.databinding.FragmentZeroshopDetailBinding


class ZeroshopDetailFragment : Fragment() {

    private var _binding: FragmentZeroshopDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentZeroshopDetailBinding.inflate(inflater, container, false)

        val imgUrl = arguments?.getString("imgUrl")


        arguments?.let {
            binding.zeroshopDetailName.text = it.getString("name")
            binding.zeroshopDetailInfo.text = it.getString("detailInfo")
            binding.phoneNumTextView.text = it.getString("phoneNum")
            Glide.with(this).load("$imgUrl").into(binding.zeroshopDetailImageView)
            binding.websiteTextView.text = it.getString("webUrl")
            binding.adressTextView.text = it.getString("adress")
            binding.runinfoTextView.text = it.getString("runInfo")

            binding.zeroshopFloatingActionButton.setOnClickListener {
                (activity as ZeroshopDetailActivity).replaceFragment(ContentWebViewFragment.newInstance())
            }
        }

        return binding.root
    }


    companion object {
        fun newInstance(): ZeroshopDetailFragment {
            return ZeroshopDetailFragment()
        }
    }
}