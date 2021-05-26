package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R
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

        var spanGuide = SpannableStringBuilder(binding.contentDetailMapGuide.text)
        spanGuide.setSpan(
                ForegroundColorSpan(
                        ContextCompat.getColor(
                                container!!.context,
                                R.color.zeroshop_point_color
                        )
                ), 19, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.contentDetailMapGuide.text = spanGuide


        arguments?.let {
            binding.zeroshopDetailName.text = it.getString("name")
            binding.zeroshopDetailInfo.text = it.getString("detailInfo")
            binding.phoneNumTextView.text = it.getString("phoneNum")
            Glide.with(this).load(imgUrl).into(binding.zeroshopDetailImageView)
            binding.websiteTextView.text = it.getString("webUrl")
            binding.adressTextView.text = it.getString("adress")
            binding.runinfoTextView.text = it.getString("runInfo")


            var spanWebUrl = SpannableString(binding.websiteTextView.text)
            spanWebUrl.setSpan(UnderlineSpan(), 0, spanWebUrl.length, 0)
            binding.websiteTextView.text = spanWebUrl

            binding.zeroshopFloatingActionButton.setOnClickListener {
                (activity as ZeroshopDetailActivity).replaceFragment(ContentWebViewFragment.newInstance())
            }

            binding.websiteTextView.setOnClickListener {
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