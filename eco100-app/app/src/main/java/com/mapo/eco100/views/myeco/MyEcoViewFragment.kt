package com.mapo.eco100.views.myeco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.eco100.R

class MyEcoViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_myeco, container, false)
    }

    companion object {
        fun newInstance(): MyEcoViewFragment {
            return MyEcoViewFragment()
        }
    }
}