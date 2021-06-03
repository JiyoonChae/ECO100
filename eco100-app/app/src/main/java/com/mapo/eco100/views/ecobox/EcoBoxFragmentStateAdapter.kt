package com.mapo.eco100.views.ecobox

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class EcoBoxFragmentStateAdapter(fragmentActivity: EcoBoxViewFragment) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = arrayListOf<Fragment>()

    fun appendFragment(fragment: Fragment) {
        fragments.add(fragment)
        println("fragment> $fragment")
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

}