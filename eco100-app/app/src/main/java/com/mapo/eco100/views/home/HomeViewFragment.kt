package com.mapo.eco100.views.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mapo.eco100.R
import me.relex.circleindicator.CircleIndicator3

class HomeViewFragment : Fragment() {

    private lateinit var pager: ViewPager2
    private lateinit var recycleGuideView : LinearLayout
    private val pageNum = 4
    private lateinit var pageIndicator: CircleIndicator3
    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // viewpager
        pager = view.findViewById(R.id.pager)
        pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        pager.offscreenPageLimit = 4

        // adapter
        val pagerAdapter = HomeFragmentStateAdapter(this)
        pagerAdapter.run {
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_volunteer))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_dacu))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_news))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_challenge))

            pager.adapter = pagerAdapter
        }

        // indicator
        pageIndicator = view.findViewById(R.id.indicator)
        pageIndicator.createIndicators(pageNum, 0)
        pageIndicator.setViewPager(pager)

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (positionOffsetPixels == 0) {
                    pager.currentItem = position
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                pageIndicator.animatePageSelected(position % pageNum)
            }
        })

        // dialog
        dialog = Dialog(view.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.home_dialog)

        // recycleGuideView
        recycleGuideView = view.findViewById(R.id.recycleGuideView)
        recycleGuideView.setOnClickListener {
            showDialog()
        }

        return view
    }

    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeBtn : ImageButton = dialog.findViewById(R.id.closeBtn)
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

