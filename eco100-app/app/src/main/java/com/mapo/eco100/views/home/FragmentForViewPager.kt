package com.mapo.eco100.views.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.common.TabItemSelector
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.EcoBoxViewFragment
import com.mapo.eco100.views.myeco.MyEco100Activity

class FragmentForViewPager : Fragment() {

    private lateinit var callback: TabItemSelector

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TabItemSelector) {
            callback = context
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_viewpager_item, container, false)
        val imageIV = view.findViewById<ImageView>(R.id.viewImage)
        val bundle = arguments
        bundle?.getInt("image", -1)?.let { Glide.with(view.context).load(it).into(imageIV) }
        val index = bundle?.getInt("index", -1)
        Log.d("home", "이동 index : $index")

        imageIV.setOnClickListener {
            when (index) {
                0 -> {
                    val intent = Intent(requireActivity(), MyEco100Activity::class.java)
                    startActivity(intent)
                    Log.d("home", "이동 index : 0")
                }
                1 -> {
                    Log.d("home", "이동 index : 1")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, CommunityViewFragment.newInstance()).commit()
                    callback.selectItem(2)
                }
                2 -> {
                    Log.d("home", "이동 index : 2")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, EcoBoxViewFragment.newInstance()).commit()
                    callback.selectItem(1)
                }
                3 -> {
                    Log.d("home", "이동 index : 3")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, EcoBoxViewFragment.newInstance()).commit()
                    callback.selectItem(1)
                }
                4 -> {
                    Log.d("home", "이동 index : 1")
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, CommunityViewFragment.newInstance()).commit()
                    callback.selectItem(2)
                }
                else -> {
                    Log.d("home", "이동 x")
                }
            }
        }

        return view
    }

    companion object {

        fun newInstance(index: Int, imageSrc: Int): FragmentForViewPager {
            val fragment = FragmentForViewPager()
            with(Bundle()) {
                putInt("index", index)
                putInt("image", imageSrc)
                fragment.arguments = this
            }
            return fragment
        }
    }
}