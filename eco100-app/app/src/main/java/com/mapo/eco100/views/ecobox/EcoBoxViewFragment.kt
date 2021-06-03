package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentEcoboxBinding
import com.mapo.eco100.views.ecobox.navigation.EcoBoxContentsFragment
import com.mapo.eco100.views.ecobox.navigation.EcoBoxRecycleGuideFragment
import com.mapo.eco100.views.ecobox.navigation.EcoBoxZeroShopFragment

class EcoBoxViewFragment : Fragment() {

    // 1) binding 설정
    private var _binding: FragmentEcoboxBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabItems: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 2) binding 작업 - fragment_ecobox 레이아웃 연결
        _binding = FragmentEcoboxBinding.inflate(inflater, container, false)

        tabItems = resources.getStringArray(R.array.ecobox_tab)

        // viewpager와 adpater 연결
        binding.viewPager.adapter = generatePagerAdapter()
        binding.viewPager.offscreenPageLimit = 3

        // tab과 viewpager 연결
        TabLayoutMediator(binding.tab, binding.viewPager) { tab, position ->
            tab.text = tabItems[position]
        }.attach()

        return binding.root
    }

    private fun generatePagerAdapter(): EcoBoxFragmentStateAdapter {
        val pagerAdapter = EcoBoxFragmentStateAdapter(this)
        with(pagerAdapter) {
            appendFragment(EcoBoxRecycleGuideFragment.newInstance())
            appendFragment(EcoBoxContentsFragment.newInstance())
            appendFragment(EcoBoxZeroShopFragment.newInstance())
        }
        return pagerAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): EcoBoxViewFragment {
            return EcoBoxViewFragment()
        }
    }
}