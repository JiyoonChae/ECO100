package com.mapo.eco100.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R

class FragmentForViewPager : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_viewpager_item, container, false)
        val imageIV = view.findViewById<ImageView>(R.id.viewImage)
        val bundle = arguments
        bundle?.getInt("image", -1)?.let { Glide.with(view.context).load(it).into(imageIV) }
        return view
    }

    companion object {

        fun newInstance(imageSrc: Int): FragmentForViewPager {
            val fragment = FragmentForViewPager()
            with(Bundle()) {
                putInt("image", imageSrc)
                fragment.arguments = this
            }
            return fragment
        }
    }
}