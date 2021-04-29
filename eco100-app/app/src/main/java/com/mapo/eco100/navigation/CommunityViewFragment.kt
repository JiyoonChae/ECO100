package com.mapo.eco100.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.eco100.R

class CommunityViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)

    }

    companion object {
        fun newInstance(): CommunityViewFragment {
            return CommunityViewFragment()
        }
    }
}