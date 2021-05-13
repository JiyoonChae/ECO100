package com.mapo.eco100.views.community

import android.content.Intent
import com.mapo.eco100.R


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import com.mapo.eco100.databinding.ActivityWriteChallengeBinding
import com.mapo.eco100.databinding.RowChallengeBinding
import java.util.jar.Manifest

class WriteChallenge : BaseActivity() {
    val PERM_STORAGE = 99 //외부 저장소 권한 처리
    val PERM_CAMERA = 100 //카메라 권한 처리
    val REQ_CAMERA =101 //카메라 촬영 요청

    val binding by lazy { ActivityWriteChallengeBinding.inflate(layoutInflater) }
    val binding2 by lazy { RowChallengeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //외부 저장소 권한 요청
        requirePermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),PERM_STORAGE)
        
        //클릭한 챌린지 리스트에있는 주제를 받아서 출력하기
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
    //저장소 권한 요청이 승인되었을 경우
    override fun permissionGranted(requestCode: Int) {
        when(requestCode){
            PERM_STORAGE -> setViews()
            PERM_CAMERA -> openCamera()
        }
    }

    //저장소 권한 요청에 대한 승인 거부되었을 경우
    override fun permissionDenied(requestCode: Int) {
        when(requestCode){
            PERM_STORAGE -> {
                Toast.makeText(baseContext, "외부저장소 권한을 승인해야 사용가능합니다", Toast.LENGTH_LONG).show()
               // finish()
            }
            PERM_CAMERA -> {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 사용할 수 있습니다", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setViews(){
        //버튼 클릭 시 카메라 권한을 요청하는 코드
        binding.buttonCamera.setOnClickListener {
            requirePermissions(arrayOf(android.Manifest.permission.CAMERA),PERM_CAMERA)
        }
    }

    //카메라 요청
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)
    }
}