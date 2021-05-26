package com.mapo.eco100.views.community

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mapo.eco100.databinding.FragmentCommunityBinding
import com.mapo.eco100.views.community.navigation.BoardFragment
import com.mapo.eco100.views.community.navigation.ChallengeFragment

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

//        binding.button.setOnClickListener {
//            var keyHash = Utility.getKeyHash(container!!.context)
//            Log.d("kakaoKeyHash", keyHash)
//
//        }
//
//        binding.button2.setOnClickListener {
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Toast.makeText(parentContext,"회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(parentContext,"회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

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