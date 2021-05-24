package com.mapo.eco100.views.myeco

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.ActivityMyChallengeListBinding
import com.mapo.eco100.entity.challenge.ChallengePostList
import com.mapo.eco100.service.ChallengeService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Response

class MyChallengeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainActivityContext:Context
    private val binding by lazy { ActivityMyChallengeListBinding.inflate(layoutInflater) }
    private var adapter: RecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        adapter = RecyclerAdapter()
        recyclerView = binding.myChallengeRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //서버연결
        val service: ChallengeService =
            NetworkSettings.retrofit.build().create(ChallengeService::class.java)

       service.challengePostList(1).enqueue(object : retrofit2.Callback<ChallengePostList>{
           override fun onFailure(call: Call<ChallengePostList>, t: Throwable) {
               Log.d("서버연결", " 실패 --------------", null)
           }

           override fun onResponse(call: Call<ChallengePostList>,response: Response<ChallengePostList>) {
               adapter!!.challegePostList = response.body() as ChallengePostList
               adapter!!.notifyDataSetChanged()

           }
       })


    }

    inner class RecyclerAdapter() : RecyclerView.Adapter<ViewHolderClass>() {
        var challegePostList: ChallengePostList? = null  //내가쓴 챌린지 목록 받을 변수

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val itemView  =layoutInflater.inflate(R.layout.row_mypage, null)

           // val itemView = RowMypageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            val holder = ViewHolderClass(itemView)
            return holder
        }

        override fun getItemCount(): Int {
            return challegePostList?.size ?: 0
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            val challengePost = challegePostList?.get(position)

            holder.date.text = challengePost?.date
            holder.subject.text = challengePost?.subject
            holder.challengeContents.text = challengePost?.contents
            Glide.with(holder.challengeImg).load(challengePost?.imageUrl).into(holder.challengeImg)

        }
    }

    class ViewHolderClass(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val subject = itemView.findViewById<TextView>(R.id.challenge_subject)
        val challengeContents = itemView.findViewById<TextView>(R.id.my_contents)
        val challengeImg = itemView.findViewById<ImageView>(R.id.my_challenge_img)
        val date = itemView.findViewById<TextView>(R.id.challenge_date)

    }

}



