package com.mapo.eco100.navigation

import android.content.Context
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
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.RowChallengeBinding
import com.mapo.eco100.entity.challenge.Challenge
import com.mapo.eco100.entity.challenge.ChallengeList
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.service.ChallengeService
import com.mapo.eco100.views.community.WriteChallenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChallengeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var  mainActivityContext: Context

    val imgRes = intArrayOf(R.drawable.icon_challenge17, R.drawable.icon_challenge19, R.drawable.icon_challenge22,
        R.drawable.icon_challenge23,R.drawable.icon_challenge8, R.drawable.icon_challenge30)

    val data1 = arrayOf("대중 교통 이용하기","플로깅 실천하기 (내가 직접 주운 쓰레기 사진 인증)", "비닐 포장, 상품 포장 하지 않기", "책 구매 대신 도서관에서 대출하기",
        "다회 용기를 이용하여 음식 및 식자재 구매하기", "종이영수증 대신 전자영수증 사용하기")


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainActivityContext = requireContext()


        val view = inflater.inflate(R.layout.fragment_challenge, container, false)
        val adapter = RecyclerAdatper()
        recyclerView = view.findViewById(R.id.challenge_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        val service: ChallengeService =
            NetworkSettings.retrofit.build().create(ChallengeService::class.java)
        service.challengeList(1).enqueue(object : Callback<ChallengeList>{
            override fun onFailure(call: Call<ChallengeList>, t: Throwable) {
                Log.d(tag, " 실패 --------------", null)
            }

            override fun onResponse(call: Call<ChallengeList>, response: Response<ChallengeList>) {
                adapter.challengeList = response.body() as ChallengeList
                adapter.notifyDataSetChanged()
            }
        })

        return view

    }

    companion object {
        fun newInstance(): ChallengeFragment {
            return ChallengeFragment()
        }
    }

    //RecyclerView의 Adapter클래스
    inner class RecyclerAdatper:RecyclerView.Adapter<RecyclerAdatper.ViewHolderClass>(){

        var challengeList: ChallengeList? = null

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
            val challenge = challengeList?.get(position)
//            holder.rowImageView.setImageResource(imgRes[position])
//            holder.rowTextView.text = data1[position]
//            holder.rowStamp1.setImageResource(R.drawable.icon_challenge_nonsucced)
//            holder.rowStamp2.setImageResource(R.drawable.icon_challenge_succed)
            holder.setChallenge(challenge)
        }

        override fun getItemCount(): Int {
            return challengeList?.size?:0
        }

        //viewHolder Class
        inner class ViewHolderClass(itemView: View) :RecyclerView.ViewHolder(itemView){
            //항목 VIEW 내부의 VIEW객체의 주소값을 담는다.
            val rowImageView = itemView.findViewById<ImageView>(R.id.rowImageView)
            val rowTextView = itemView.findViewById<TextView>(R.id.rowTextView)
            val rowStamp1 = itemView.findViewById<ImageView>(R.id.rowStamp1)
            val rowStamp2 = itemView.findViewById<ImageView>(R.id.rowStamp2)
            val participants = itemView.findViewById<TextView>(R.id.participants)

            fun setChallenge(challenge: Challenge?){
                challenge?.let{
                    rowTextView.text=challenge.subject
                    participants.text = challenge.numOfParticipants.toString()
                    Glide.with(rowImageView).load(challenge.imageUrl).into(rowImageView)
                    rowStamp1.setImageResource(R.drawable.icon_challenge_nonsucced)
                    rowStamp2.setImageResource(R.drawable.icon_challenge_nonsucced)
                }


            }

        }


    }
    val binding2 by lazy { RowChallengeBinding.inflate(layoutInflater) }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //resultCode가 오면 if(resultCode == ) 버튼 바꿔라~이런식으로 쓰면됨
        binding2.rowStamp1.setImageResource(R.drawable.emoji)
//        var bundle = Bundle()
//        bundle.putString("key", "미션 완료!")

    }

}