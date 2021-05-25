package com.mapo.eco100.views.ecobox.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentContentsBinding
import com.mapo.eco100.entity.staticmodel.Contents

class EcoBoxContentsFragment : Fragment() {

    private var _binding: EcoboxFragmentContentsBinding? = null
    private val binding get() = _binding!!
    private lateinit var contentRecyclerView: RecyclerView

    var adapter = ContentRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EcoboxFragmentContentsBinding.inflate(inflater, container, false)

        contentRecyclerView = binding.ecoboxContentRecyclerview
        contentRecyclerView.adapter = adapter
        contentRecyclerView.layoutManager = GridLayoutManager(context, 2)

        adapter.data = mutableListOf(
            Contents(
                "[다큐]",
                "분리수거가 당신에게 가르쳐 주지 않는 것",
                R.drawable.ecobox_content_documentary2,
                "https://youtu.be/baO98yBvJ5M",
                "우리 사회 상당수의 사람들이 분리배출에 많은 노력과 시간을 들이고 있습니다.\n" +
                        "그러나 분리된 폐기물들이 어떠한 과정을 거쳐 우리 사회로 돌아오는지, \n" +
                        "과연 재활용이 제대로 되고 있는지에 대해서는 잘 알지 못합니다. \n" +
                        "정말로 분리배출만 잘하면 되는 것일까요? \n" +
                        "생활 폐기물이 분리배출 된 이후의 과정과 현 실태를 알아보았습니다."
            ),
            Contents(
                "[환경툰]",
                "먹깨비툰 1화 '배달용기' 편",
                R.drawable.ecobox_content_webtoon1,
                "https://1boon.kakao.com/mediaseoul/6080c81240df72357a3f0016",
                "먹깨비 시즌의 시작을 알리는 1화! \n우리에겐 용기가 있다... \n아무도 날 막지 못해! \n먹깨비 시즌 2 배달용기 편!"
            ),
            Contents(
                "[다큐]",
                "플라스틱 없이 살아보기 part 1",
                R.drawable.ecobox_content_documentary1,
                "https://youtu.be/DK4p_vitghM",
                "조물주는 인간을 만들고, 인간은 플라스틱을 만들었다? \n" +
                        "20세기 최고의 발명품 플라스틱. \n" +
                        "그러나 사용 속도보다 턱없이 느린 분해 속도에, 전 세계가 몸살을 앓기 시작했다. \n" +
                        "재활용되지 못한 플라스틱은 만드는 데 5초, 사용하는 데 5분 남짓의 짧은 생을 마감하고 땅에 묻힌다. \n" +
                        "플라스틱이 발명된 지 112년, 인류 최초로 사용한 플라스틱을 땅에 묻었다면 여전히 원형 그대로 남아있을지 모를 일이다. \n" +
                        "처리되지 못한 플라스틱은 세포 보다 작은 미세 플라스틱이 되어 인간 세계로 다가오고 있다. \n" +
                        "플라스틱 없이 몇 시간이나 버틸 수 있을까? \n" +
                        "도전자들이 플라스틱 줄이기에 성공할 수 있을지 함께 알아본다."
            ),
            Contents(
                "[대외활동]",
                "제9회 지구사랑 환경 그림그리기 공모전",
                R.drawable.ecobox_content_activity2,
                "https://www.gimhae.go.kr/06295/06296.web?gcode=1376&idx=2455979&amode=view&",
                "제9회 지구사랑 환경 그림그리기 공모전 요강 \n" +
                        "□ 개 요 \n" +
                        "○ 공모기간 : 2021. 5. 3(월) ~ 5. 24(월), 22일간 \n" +
                        "○ 작품주제 : 지구사랑 환경 그림그리기 상상화 \n" +
                        "○ 그림규격 : 8절 도화지(크레파스, 수채화 등 그림재료 자유) \n" +
                        "※ 예) 잡지, 과장봉지, 털실 등 활용 가능 \n" +
                        "○ 참가대상 : 유치부 및 초등학생 \n" +
                        "○ 주최/주관 : 김해시기후변화홍보체험관(경남녹색환경지원센터), 김해시 \n" +
                        "○ 후 원 : 김해교육지원청, (사)환경미술협회 경상남도지회, (사)환경미술협회 김해지부 \n" +
                        "□ 작품제출방법 \n" +
                        "○ 신청방법 - 그림 출품서 작성 후 반드시 그림 뒷면에 부착 - 1인 1작품 그림에 손상이 없도록 직접방문(토,일요일은 휴관으로 접수불가) 또는 우편 제출 \n" +
                        "※ 우편 제출 시 마감일 18:00 이전 우편 소인분까지 접수 \n" +
                        "○ 그림 출품서 다운로드 방법 : 김해시 기후변화홍보체험관 누리집(http://cce.gimhae.go.kr/) 공지사항 참고 \n" +
                        "○ 미술작품 보내는 곳 : 경남 김해시 화목로 334(화목동) 김해시기후변화홍보체험관 (우편번호 : 51010) \n" +
                        "○ 문의 : 055-321-2858"
            ),
            Contents(
                "[대외활동]",
                "2021 한국석유공사 기업광고 포스터 공모전",
                R.drawable.ecobox_content_activity1,
                "https://www.knoc.co.kr/sub05/sub05_5_5.jsp?num=176&mode=view&grp=null",
                "- 안녕하세요! 한국석유공사입니다! \n" +
                        "2021년 환경과 사회를 생각하는 한국석유공사 기업광고 포스터 공모전이 개최됩니다. \n" +
                        "대한민국 대표 에너지 공기업 한국석유공사의 이미지를 만들어줄 기가막힌 포스터를 애타게 기다리고 있습니다. \n" +
                        "여러분들의 참신한 표현력과 놀라운 금손 능력 맘껏 뽐내주세요! 여러분의 많은 관심과 사랑 원해요!"
            ),
            Contents(
                "[환경툰]",
                "먹깨비즈의 슬기로운 집콕생활'분리배출숏툰 2화 - 음식",
                R.drawable.ecobox_content_webtoon2,
                "https://1boon.kakao.com/mediaseoul/5f239f6076e6701e570e4e93",
                "오늘도 배달음식 시켰니? 먹깨비가 알려주는 분리배출 숏툰"
            )
        )

        return binding.root
    }

    companion object {
        fun newInstance(): EcoBoxContentsFragment {
            return EcoBoxContentsFragment()
        }
    }
}