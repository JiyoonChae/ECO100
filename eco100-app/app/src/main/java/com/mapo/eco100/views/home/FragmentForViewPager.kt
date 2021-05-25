package com.mapo.eco100.views.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.common.TabItemSelector
import com.mapo.eco100.entity.staticmodel.Contents
import com.mapo.eco100.views.community.CommunityViewFragment
import com.mapo.eco100.views.ecobox.ContentDetailActivity
import com.mapo.eco100.views.ecobox.ContentDetailFragment
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
                }
                1 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.contents, CommunityViewFragment.newInstance()).commit()
                    callback.selectItem(2)
                }
                2 -> {
                    val intent = Intent(requireActivity(), ContentDetailActivity::class.java)
                    intent.apply {
                        this.putExtra("category", "[다큐]")
                        this.putExtra("title", "플라스틱 없이 살아보기 part 1")
                        this.putExtra("imgRes", R.drawable.ecobox_content_documentary1)
                        this.putExtra(
                            "detail", "조물주는 인간을 만들고, 인간은 플라스틱을 만들었다? \n" +
                                    "20세기 최고의 발명품 플라스틱. \n" +
                                    "그러나 사용 속도보다 턱없이 느린 분해 속도에, 전 세계가 몸살을 앓기 시작했다. \n" +
                                    "재활용되지 못한 플라스틱은 만드는 데 5초, 사용하는 데 5분 남짓의 짧은 생을 마감하고 땅에 묻힌다. \n" +
                                    "플라스틱이 발명된 지 112년, 인류 최초로 사용한 플라스틱을 땅에 묻었다면 여전히 원형 그대로 남아있을지 모를 일이다. \n" +
                                    "처리되지 못한 플라스틱은 세포 보다 작은 미세 플라스틱이 되어 인간 세계로 다가오고 있다. \n" +
                                    "플라스틱 없이 몇 시간이나 버틸 수 있을까? \n" +
                                    "도전자들이 플라스틱 줄이기에 성공할 수 있을지 함께 알아본다."
                        )
                        this.putExtra(
                            "webUrl",
                            "https://youtu.be/DK4p_vitghM"
                        )
                    }
                    ContextCompat.startActivity(requireContext(), intent, null)
                }
                3 -> {
                    val intent = Intent(requireActivity(), ContentDetailActivity::class.java)
                    intent.apply {
                        this.putExtra("category", "[대외활동]")
                        this.putExtra("title", "2021 한국석유공사 기업광고 포스터 공모전")
                        this.putExtra("imgRes", R.drawable.ecobox_content_activity1)
                        this.putExtra(
                            "detail", "- 안녕하세요! 한국석유공사입니다! \n" +
                                    "2021년 환경과 사회를 생각하는 한국석유공사 기업광고 포스터 공모전이 개최됩니다. \n" +
                                    "대한민국 대표 에너지 공기업 한국석유공사의 이미지를 만들어줄 기가막힌 포스터를 애타게 기다리고 있습니다. \n" +
                                    "여러분들의 참신한 표현력과 놀라운 금손 능력 맘껏 뽐내주세요! 여러분의 많은 관심과 사랑 원해요!"
                        )
                        this.putExtra(
                            "webUrl",
                            "https://www.knoc.co.kr/sub05/sub05_5_5.jsp?num=176&mode=view&grp=null"
                        )
                    }
                    ContextCompat.startActivity(requireContext(), intent, null)
                }
                4 -> {
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