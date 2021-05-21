package com.mapo.eco100.views.myeco

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R

class MyChallengeListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    val data1 = arrayOf("대중 교통 이용하기","플로깅 실천하기 (내가 직접 주운 쓰레기 사진 인증)", "비닐 포장, 상품 포장 하지 않기", "책 구매 대신 도서관에서 대출하기",
        "다회 용기를 이용하여 음식 및 식자재 구매하기", "종이영수증 대신 전자영수증 사용하기")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_challenge_list)
    }
}