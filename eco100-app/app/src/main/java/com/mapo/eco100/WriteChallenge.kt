package com.mapo.eco100

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.mapo.eco100.databinding.ActivityWriteChallengeBinding
import com.mapo.eco100.databinding.RowChallengeBinding

class WriteChallenge : AppCompatActivity() {
    val binding by lazy { ActivityWriteChallengeBinding.inflate(layoutInflater) }
    val binding2 by lazy { RowChallengeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val item = intent.extras?.get("item") as String
        binding.textView.setText(item)

        binding.challengeFinish.setOnClickListener {
            //db로 데이터 전송하고 다시 프래그먼트 (챌린지 리스트로) 돌아가기
            //돌아가서 스티커 이미지 변경시키기.
          binding2.rowStamp1.setImageResource(R.drawable.emoji)
            var bundle = Bundle()
            bundle.putString("key", "미션 완료!")
            finish()


        }

    }
}