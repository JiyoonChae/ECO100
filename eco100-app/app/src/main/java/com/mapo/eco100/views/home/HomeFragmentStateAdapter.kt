package com.mapo.eco100.views.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeFragmentStateAdapter(fragmentActivity: HomeViewFragment) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = arrayListOf<Fragment>()
    fun appendFragment(fragment: Fragment) {
        fragments.add(fragment)
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}