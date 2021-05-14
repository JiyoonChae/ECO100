package com.mapo.eco100.views.myeco

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.mapo.eco100.R
import com.mapo.eco100.views.MainActivity

class MyEcoViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_myeco, container, false)

        val boardList = view.findViewById<TextView>(R.id.my_board_list)
        val challengeList = view.findViewById<TextView>(R.id.my_challenge_list)
        val badgeList = view.findViewById<TextView>(R.id.my_badge_list)

        boardList.setOnClickListener {
            val intent = Intent(activity, MyBoardListActivity::class.java)
            startActivity(intent)
        }

        challengeList.setOnClickListener {
            val intent = Intent(activity, MyChallengeListActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    companion object {
        fun newInstance(): MyEcoViewFragment {
            return MyEcoViewFragment()
        }
    }
}