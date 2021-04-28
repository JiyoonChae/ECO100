package com.mapo.eco100.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kakao.sdk.common.util.Utility
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentCommunityBinding

class CommunityViewFragment : Fragment() {

    private var _binding : FragmentCommunityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.button.setOnClickListener {
            var keyHash = Utility.getKeyHash(container!!.context)
            Log.d("kakaoKeyHash", keyHash)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): CommunityViewFragment {
            return CommunityViewFragment()
        }
    }
}