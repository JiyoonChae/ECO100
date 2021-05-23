package com.mapo.eco100.views.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.common.TabItemSelector
import com.mapo.eco100.databinding.FragmentHomeBinding
import com.mapo.eco100.views.map.MapViewFragment
import com.mapo.eco100.views.search.SearchActivity

class HomeViewFragment : Fragment() {

    private val pageNum = 5
    private lateinit var recycleGuideView: LinearLayout
    private lateinit var dialog: Dialog
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var callback: TabItemSelector

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is TabItemSelector) {
            callback = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // viewpager
        binding.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pager.offscreenPageLimit = 5

        // adapter
        val pagerAdapter = HomeFragmentStateAdapter(this)
        pagerAdapter.run {
            appendFragment(FragmentForViewPager.newInstance(0, R.drawable.img_home_mid_eco))
            appendFragment(FragmentForViewPager.newInstance(1, R.drawable.img_home_mid_chall))
            appendFragment(FragmentForViewPager.newInstance(2, R.drawable.img_home_mid_news))
            appendFragment(FragmentForViewPager.newInstance(3, R.drawable.img_home_mid_voluntee))
            appendFragment(FragmentForViewPager.newInstance(4, R.drawable.img_home_mid_comm))
            binding.pager.adapter = pagerAdapter
        }

        // indicator
        binding.indicator.createIndicators(pageNum, 0)
        binding.indicator.setViewPager(binding.pager)

        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    binding.pager.currentItem = position
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.indicator.animatePageSelected(position % pageNum)
            }
        })

        // toolbar
        binding.toolbar.inflateMenu(R.menu.app_bar_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    val intent = Intent(view.context, SearchActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        // dialog
        dialog = Dialog(view.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.home_dialog)

        // recycleGuideView
        recycleGuideView = view.findViewById(R.id.recycleGuideView)
        recycleGuideView.setOnClickListener {
            showDialog()
        }

        binding.goMap.setOnClickListener {
            replaceMapFragment()
        }

        Glide.with(this).load(R.drawable.img_home_go_map).into(binding.mapImg)
        return view
    }

    private fun replaceMapFragment() {
        val fm: FragmentManager? = fragmentManager
        val ft = fm?.beginTransaction()
        ft?.replace(R.id.contents, MapViewFragment.newInstance())?.commit()
        callback.selectItem(3)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeBtn: AppCompatButton = dialog.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    companion object {
        fun newInstance(): HomeViewFragment {
            return HomeViewFragment()
        }
    }
}

