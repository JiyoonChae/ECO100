package com.mapo.eco100.views.community

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentCommunityBinding

class CommunityViewFragment : Fragment() {

    private var _binding : FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentContext: Context

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommunityBinding.inflate(inflater,container,false)
        parentContext = container!!.context

        binding.button.setOnClickListener {
            var keyHash = Utility.getKeyHash(container!!.context)
            Log.d("kakaoKeyHash", keyHash)
        }

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("참여 게시판"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("챌린지"))

        val pagerAdapter = PagerAdapter(childFragmentManager)
        binding.viewPager.adapter = pagerAdapter

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        return binding.root
    }

    class PagerAdapter(
        fragmentManager: FragmentManager
    ) : FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {
            return when(position) {
                0 -> {
                    BoardFragment()
                }
                1 -> {
                    ChallengeFragment()
                }
                else -> {
                    BoardFragment()
                }
            }
        }

        override fun getCount(): Int {
            return 2
        }
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