package com.mapo.eco100.views.myeco

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mapo.eco100.views.login.KakaoLoginUtils
import com.mapo.eco100.R
import com.mapo.eco100.views.login.NoLoginDialog

class MyEcoViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myeco, container, false)

        val boardList = view.findViewById<View>(R.id.my_board_list_frame)
        val challengeList = view.findViewById<View>(R.id.my_challenge_list_frame)
        val badgeList = view.findViewById<View>(R.id.my_badge_list_frame)
        val commentList = view.findViewById<View>(R.id.my_comment_list_frame)
        val eco100 = view.findViewById<View>(R.id.eco100)
        val logout = view.findViewById<View>(R.id.logout)
        val removeAccount = view.findViewById<View>(R.id.delete)

        val nickname = view.findViewById<TextView>(R.id.my_nickName)
        nickname.text = requireContext().getSharedPreferences("login",Context.MODE_PRIVATE).getString("nickname","")

        boardList.setOnClickListener {
            val intent = Intent(activity, MyBoardListActivity::class.java)
            startActivity(intent)
        }

        challengeList.setOnClickListener {
            val intent = Intent(activity, MyChallengeListActivity::class.java)
            startActivity(intent)
        }

        badgeList.setOnClickListener {
            val intent = Intent(activity, MyBadgeListActivity::class.java)
            startActivity(intent)
        }

        commentList.setOnClickListener {
            val intent = Intent(activity, MyCommentListActivity::class.java)
            startActivity(intent)
        }

        eco100.setOnClickListener {
            val intent = Intent(activity, MyEco100Activity::class.java)
            startActivity(intent)
        }

        logout.setOnClickListener {
            KakaoLoginUtils(requireContext()).logout()
            val dialog = NoLoginDialog(requireContext())
            dialog.show()
        }

        removeAccount.setOnClickListener {
            KakaoLoginUtils(requireContext()).deleteAccount()
        }

        return view
    }

    companion object {
        fun newInstance(): MyEcoViewFragment {
            return MyEcoViewFragment()
        }
    }
}