package com.mapo.eco100.views.community.navigation

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.FragmentChallengeBinding
import com.mapo.eco100.entity.challenge.Challenge
import com.mapo.eco100.entity.challenge.ChallengeList
import com.mapo.eco100.service.ChallengeService
import com.mapo.eco100.views.community.WriteChallenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChallengeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private lateinit var mainActivityContext: Context
    private val binding by lazy { FragmentChallengeBinding.inflate(layoutInflater) }
    private lateinit var itemView: View
    val challengeCode = 777
    private lateinit var dialog: Dialog
    private var adapter: RecyclerAdatper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainActivityContext = requireContext()


        val view = inflater.inflate(R.layout.fragment_challenge, container, false)
        adapter = RecyclerAdatper()
        recyclerView = view.findViewById(R.id.challenge_recycler)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        val service: ChallengeService =
            NetworkSettings.retrofit.build().create(ChallengeService::class.java)
        service.challengeList(1).enqueue(object : Callback<ChallengeList> {
            override fun onFailure(call: Call<ChallengeList>, t: Throwable) {
                Log.d(tag, " 실패 --------------", null)
            }

            override fun onResponse(call: Call<ChallengeList>, response: Response<ChallengeList>) {
                adapter!!.challengeList = response.body() as ChallengeList
                adapter!!.notifyDataSetChanged()
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
    inner class RecyclerAdatper : RecyclerView.Adapter<RecyclerAdatper.ViewHolderClass>() {

        var challengeList: ChallengeList? = null

        //viewholder 객체가 필요할 때 호출되는 메서드 :항목 구성을 위한 layout 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            //항목으로 사용할 view객체 생성.
            itemView = layoutInflater.inflate(R.layout.row_challenge, null)
            val holder = ViewHolderClass(itemView)

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val challenge = challengeList?.get(position)


//            holder.rowImageView.setImageResource(imgRes[position])
//            holder.rowTextView.text = data1[position]
//            holder.rowStamp1.setImageResource(R.drawable.icon_challenge_nonsucced)
//            holder.rowStamp2.setImageResource(R.drawable.icon_challenge_succed)
            challenge?.let {
                holder.rowTextView.text = challenge.subject
                holder.participants.text = challenge.numOfParticipants.toString()
                Glide.with(holder.rowImageView).load(challenge.imageUrl).into(holder.rowImageView)
                //만약 해당 챌린지의 myParticipationCnt 가 1이면 rowstamp1이 글읽기로 그
                when(challenge.myParticipationCnt) {
                    0 -> {
                        Glide.with(holder.rowStamp1).load(R.drawable.icon_challenge_nonsucced).into(holder.rowStamp1)
                        Glide.with(holder.rowStamp2).load(R.drawable.icon_challenge_nonsucced).into(holder.rowStamp2)
                    }
                    1 -> {
                        Glide.with(holder.rowStamp1).load(R.drawable.icon_challenge_succed).into(holder.rowStamp1)
                        Glide.with(holder.rowStamp2).load(R.drawable.icon_challenge_nonsucced).into(holder.rowStamp2)
                    }
                    2 -> {
                        Glide.with(holder.rowStamp1).load(R.drawable.icon_challenge_succed).into(holder.rowStamp1)
                        Glide.with(holder.rowStamp2).load(R.drawable.icon_challenge_succed).into(holder.rowStamp2)
                    }
                    else -> {

                    }
                }

            }

            holder.rowStamp1.setOnClickListener {
                when(challenge!!.myParticipationCnt) {
                    0 -> {
                        showDialog(challenge)
                    }
                    1 -> {
                       //글썼던 걸로 이동

                    }
                    2 -> {
                       //글썼던 걸로 이동
                    }
                    else -> {

                    }
                }

            }

            holder.rowStamp2.setOnClickListener {
                //Log.d("click", "clicked!!")
                when(challenge!!.myParticipationCnt) {
                    0 -> {
                        showDialog(challenge)
                    }
                    1 -> {
                        showDialog(challenge)

                    }
                    2 -> {
                        //글썼던 걸로 이동
                    }
                    else -> {

                    }
                }
            }

        }

        override fun getItemCount(): Int {
            return challengeList?.size ?: 0
        }

        //viewHolder Class
        inner class ViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
            //항목 VIEW 내부의 VIEW객체의 주소값을 담는다.
            val rowImageView = itemView.findViewById<ImageView>(R.id.rowImageView)
            val rowTextView = itemView.findViewById<TextView>(R.id.rowTextView)
            val rowStamp1 = itemView.findViewById<ImageView>(R.id.rowStamp1)
            val rowStamp2 = itemView.findViewById<ImageView>(R.id.rowStamp2)
            val participants = itemView.findViewById<TextView>(R.id.participants)


        }




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            Log.d("챌린지리스트: ", "resultCode : $resultCode")
            Log.d("챌린지리스트: ", "requestCode: $requestCode")
            when (requestCode) {
                challengeCode -> {
                    val service: ChallengeService =
                        NetworkSettings.retrofit.build().create(ChallengeService::class.java)
                    service.challengeList(1).enqueue(object : Callback<ChallengeList> {
                        override fun onFailure(call: Call<ChallengeList>, t: Throwable) {
                            Log.d(tag, " 실패 --------------", null)
                        }

                        override fun onResponse(
                            call: Call<ChallengeList>,
                            response: Response<ChallengeList>
                        ) {
                            if(response.isSuccessful) {
                                adapter?.challengeList = response.body() as ChallengeList
                                adapter?.notifyDataSetChanged()
                                Log.d("챌린지리스트: ", "새로고침완료")
                            }
                        }
                    })
                }
            }


        } else {
            Log.d("result: ", "실패??")
        }
        //resultCode가 오면 if(resultCode == ) 버튼 바꿔라~이런식으로 쓰면됨


    }

    //dialog
    private fun showDialog(challenge: Challenge?) {
        // dialog
        dialog = Dialog(binding.root.context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_write_challenge)
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okBtn: AppCompatButton = dialog.findViewById(R.id.challenge_popup_ok)
        okBtn.setOnClickListener {
            //글쓰기 화면 띄우기
            val intent = Intent(activity, WriteChallenge::class.java)
            dialog.dismiss()

            //선택한 주제 전달
            intent.putExtra("item", challenge)

            startActivityForResult(intent, challengeCode)
        }
        val cxlBtn: AppCompatButton = dialog.findViewById(R.id.challenge_popup_cancel)
        cxlBtn.setOnClickListener {
            dialog.dismiss()
        }
    }


}

