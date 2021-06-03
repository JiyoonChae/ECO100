package com.mapo.eco100.views.myeco

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.R


class BadgeDialogFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflater.inflate(R.layout.fragment_badge_dialog, container, false)
        val num = arguments?.getString("num")
        val check = arguments?.getString("check")
        val badge = binding.findViewById<ImageView>(R.id.badge_img)
        val badgeText = binding.findViewById<TextView>(R.id.badge_text)

        Log.d("뱃지", "$check")
        if (check =="true"){
            when (num) {
                "1" -> {
                    Log.d("프래그", "1")
                    badge.setImageResource(R.drawable.btn1)
                }
                "2" -> {
                    Log.d("프래그", "true 2")
                    badge.setImageResource(R.drawable.badge2)
                    badgeText.text = "첫번째 게시글 작성하고 \n환경 정보 및 꿀팁을 나누어요"
                }
                "3" -> {
                    Log.d("프래그", "true 3")
                    badge.setImageResource(R.drawable.badge3)
                    badgeText.text = "첫번째 댓글을 달아주세요"
                }
                "4" -> {
                    Log.d("프래그", "true 4")
                    badge.setImageResource(R.drawable.badge4)
                    badgeText.text = "커뮤니티 게시글을 10개 이상 작성해요"
                }
                "5" -> {
                    badge.setImageResource(R.drawable.badge5)
                    badgeText.text = "커뮤니티 게시글에 \n 댓글을 10개 이상 작성해요"
                }
                "6" -> {
                    badge.setImageResource(R.drawable.badge6)
                    badgeText.text = "커뮤니티 게시글에 \n 좋아요를 10개 이상 눌러요"
                }
                "7" -> {
                    badge.setImageResource(R.drawable.badge7)
                    badgeText.text = "환경 챌린지를 시작합니다 \n 첫 달 미션을 모두 완료해요"
                }
                "8" -> {
                    badge.setImageResource(R.drawable.badge8)
                    badgeText.text = "환경 챌린지 레벨업! \n 두번째 달 미션을 모두 완료해요"
                }
                "9" -> {
                    badge.setImageResource(R.drawable.badge9)
                    badgeText.text = "환경 챌린지 홀릭! \n 세번째 달 미션을 모두 완료해요"
                }
                "10" -> {
                    badge.setImageResource(R.drawable.badge10)
                    badgeText.text = "환경 지킴이로 신뢰를 쌓아요 \n 넷째 달 미션을 모두 완료해요"
                }
                "11" -> {
                    badge.setImageResource(R.drawable.badge11)
                    badgeText.text = "환경을 사랑하는 당신, \n 다섯번째 달 미션을 모두 완료해요"
                }
                "12" -> {
                    badge.setImageResource(R.drawable.badge12)
                    badgeText.text = "당신은 환경 챌린지 달인! \n 여섯번째 달 미션을 모두 완료해요"
                }
            }
        }else {
            when (num) {
                "1" -> {
                    Log.d("프래그", "false 1")
                    badge.setImageResource(R.drawable.btn1)
                }
                "2" -> {
                    Log.d("프래그", "false 2")
                    badge.setImageResource(R.drawable.btn2)
                    badgeText.text = "첫번째 게시글 작성하고 \n환경 정보 및 꿀팁을 나누어요"
                }
                "3" -> {
                    Log.d("프래그", "false 3")
                    badge.setImageResource(R.drawable.btn3)
                    badgeText.text = "첫번째 댓글을 달아주세요"
                }
                "4" -> {
                    Log.d("프래그", "false 4")
                    badge.setImageResource(R.drawable.btn4)
                    badgeText.text = "커뮤니티 게시글을 10개 이상 작성해요"
                }
                "5" -> {
                    badge.setImageResource(R.drawable.btn5)
                    badgeText.text = "커뮤니티 게시글에 \n 댓글을 10개 이상 작성해요"
                }
                "6" -> {
                    badge.setImageResource(R.drawable.btn6)
                    badgeText.text = "커뮤니티 게시글에 \n 좋아요를 10개 이상 눌러요"
                }
                "7" -> {
                    badge.setImageResource(R.drawable.btn7)
                    badgeText.text = "환경 챌린지를 시작합니다 \n 첫 달 미션을 모두 완료해요"
                }
                "8" -> {
                    badge.setImageResource(R.drawable.btn8)
                    badgeText.text = "환경 챌린지 레벨업! \n 두번째 달 미션을 모두 완료해요"
                }
                "9" -> {
                    badge.setImageResource(R.drawable.btn9)
                    badgeText.text = "환경 챌린지 홀릭! \n 세번째 달 미션을 모두 완료해요"
                }
                "10" -> {
                    badge.setImageResource(R.drawable.btn10)
                    badgeText.text = "환경 지킴이로 신뢰를 쌓아요 \n 넷째 달 미션을 모두 완료해요"
                }
                "11" -> {
                    badge.setImageResource(R.drawable.btn11)
                    badgeText.text = "환경을 사랑하는 당신, \n 다섯번째 달 미션을 모두 완료해요"
                }
                "12" -> {
                    badge.setImageResource(R.drawable.btn12)
                    badgeText.text = "당신은 환경 챌린지 달인! \n 여섯번째 달 미션을 모두 완료해요"
                }
            }
        }


        return binding
    }


}