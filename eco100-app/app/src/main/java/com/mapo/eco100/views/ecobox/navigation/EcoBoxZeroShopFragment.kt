package com.mapo.eco100.views.ecobox.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.eco100.R

class EcoBoxZeroShopFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.ecobox_fragment_zeroshop, container, false)

    }

    companion object {
        fun newInstance(): EcoBoxZeroShopFragment {
            return EcoBoxZeroShopFragment()
        }
    }
}