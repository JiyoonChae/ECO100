package com.mapo.eco100.views.myeco

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityMyChallengeListBinding
import com.mapo.eco100.databinding.RowMypageBinding
import com.mapo.eco100.entitiy.challenge.ChallengePostList

class MyChallengeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mainActivityContext:Context
    private val binding by lazy { ActivityMyChallengeListBinding.inflate(layoutInflater) }
    private var adapter: RecyclerAdapter? = null

    val data1 = arrayOf("대중 교통 이용하기","플로깅 실천하기 (내가 직접 주운 쓰레기 사진 인증)", "비닐 포장, 상품 포장 하지 않기", "책 구매 대신 도서관에서 대출하기",
        "다회 용기를 이용하여 음식 및 식자재 구매하기", "종이영수증 대신 전자영수증 사용하기")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = RecyclerAdapter()
        recyclerView = binding.myChallengeRecycler
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        //서버연결

    }


}

class RecyclerAdapter() : RecyclerView.Adapter<ViewHolderClass>() {
    var challegePostList: ChallengePostList? = null  //내가쓴 챌린지 목록 받을 변수

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = RowMypageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolderClass(itemView)
    }

    override fun getItemCount(): Int {
        return challegePostList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

    }
}

class ViewHolderClass(val itemView: RowMypageBinding) : RecyclerView.ViewHolder(binding.root) {

}