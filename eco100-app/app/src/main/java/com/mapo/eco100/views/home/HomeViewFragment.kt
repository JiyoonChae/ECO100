package com.mapo.eco100.views.home

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentHomeBinding

class HomeViewFragment : Fragment() {

    private lateinit var recycleGuideView: LinearLayout
    private val pageNum = 5
    private lateinit var dialog: Dialog
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_eco100))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_volunteer))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_dacu))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_news2))
            appendFragment(FragmentForViewPager.newInstance(R.drawable.img_home_mid_challenge))

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeBtn: ImageButton = dialog.findViewById(R.id.closeBtn)
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

