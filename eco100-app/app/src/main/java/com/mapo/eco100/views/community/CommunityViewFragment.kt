package com.mapo.eco100.views.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentCommunityBinding
import com.mapo.eco100.navigation.BoardFragment
import com.mapo.eco100.navigation.ChallengeFragment

class CommunityViewFragment : Fragment() {

    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        parentContext = container!!.context

        binding.button.setOnClickListener {
            var keyHash = Utility.getKeyHash(container!!.context)
            Log.d("kakaoKeyHash", keyHash)
        }

        val pagerAdapter = PagerAdapter(requireActivity())
        pagerAdapter.addFragment(BoardFragment())
        pagerAdapter.addFragment(ChallengeFragment())

        binding.viewPager.adapter = pagerAdapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {})

        val tabItems = listOf("참여 게시판", "챌린지")
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabItems[position]
        }.attach()

        return binding.root
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

class PagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {
    var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }
}