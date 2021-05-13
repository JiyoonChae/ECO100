package com.mapo.eco100.navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.views.community.WriteChallenge


class ChallengeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    val imgRes = intArrayOf(R.drawable.eco1, R.drawable.eco2, R.drawable.eco3, R.drawable.eco4,
        R.drawable.eco5, R.drawable.eco6)

    val data1 = arrayOf("텀블러 사용하기", "장바구니 사용하기", "양치컵 사용하기",
        "안쓰는 방 전등끄기", "자전거 타기", "분리수거 잘하기")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_challenge, container, false)
        val adapter = RecyclerAdatper()
        recyclerView = view.findViewById(R.id.challenge_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        return view

    }

    companion object {
        fun newInstance(): ChallengeFragment {
            return ChallengeFragment()
        }
    }

    //RecyclerView의 Adapter클래스
    inner class RecyclerAdatper:RecyclerView.Adapter<RecyclerAdatper.ViewHolderClass>(){

        //viewholder 객체가 필요할 때 호출되는 메서드 :항목 구성을 위한 layout 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            //항목으로 사용할 view객체 생성.
            val itemView = layoutInflater.inflate(R.layout.row_challenge, null)
            val holder = ViewHolderClass(itemView)

            holder.rowStamp1.setOnClickListener {
                Log.d("click", "clicked!!")
                //글쓰기 화면 띄우기
                val intent = Intent(activity, WriteChallenge::class.java)
                //선택한 주제 전달
                intent.putExtra("item", holder.rowTextView.text)
                startActivity(intent)
            }

            holder.rowStamp2.setOnClickListener {
                Log.d("click", "clicked!!")
                val intent = Intent(activity, WriteChallenge::class.java)
                intent.putExtra("item", holder.rowTextView.text)
                startActivity(intent)
            }

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowImageView.setImageResource(imgRes[position])
            holder.rowTextView.text = data1[position]
            holder.rowStamp1.setImageResource(R.drawable.eco7)
            holder.rowStamp2.setImageResource(R.drawable.eco7)


        }

        override fun getItemCount(): Int {
            return imgRes.size
        }

        //viewHolder Class
        inner class ViewHolderClass(itemView: View) :RecyclerView.ViewHolder(itemView){
            //항목 VIEW 내부의 VIEW객체의 주소값을 담는다.
            val rowImageView = itemView.findViewById<ImageView>(R.id.rowImageView)
            val rowTextView = itemView.findViewById<TextView>(R.id.rowTextView)
            val rowStamp1 = itemView.findViewById<ImageView>(R.id.rowStamp1)
            val rowStamp2 = itemView.findViewById<ImageView>(R.id.rowStamp2)

        }


    }

}