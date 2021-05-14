package com.mapo.eco100.views.community


import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityWriteChallengeBinding
import com.mapo.eco100.databinding.RowChallengeBinding
import java.io.IOException
import java.text.SimpleDateFormat

class WriteChallenge : BaseActivity() {
    val PERM_STORAGE = 99 //외부 저장소 권한 처리
    val PERM_CAMERA = 100 //카메라 권한 처리
    val REQ_CAMERA =101 //카메라 촬영 요청
    val REQ_STORAGE =102 //갤러리 요청
    var realUri: Uri? = null //이미지 uri가져오기

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

        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    //갤러리 호출
    fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK) //인텐트 파라미트로 action_pick사용
        intent.type = MediaStore.Images.Media.CONTENT_TYPE //여기서 설정한 데이터를 미디어 스토어에서 불러와 선택가능
        startActivityForResult(intent, REQ_STORAGE)
    }

    //카메라 요청
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQ_CAMERA)

        createImageUri(newFileName(),"image/jpg")?.let { uri ->
            realUri =uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQ_CAMERA)
        }
    }

    //이미지를 미디어 스토어에 생성
    fun createImageUri(filename:String, mimeType:String):Uri? {
        var values = ContentValues() //(파일명, 파일타입 입력)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)
    }

    //파일 이름 생성
    fun newFileName(): String {
        val sdf= SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        //파일명 : 연월일_시간.jpg
        return "$filename.jpg"
    }


    //Uri를 사용해서 저장된 이미지 호출
    fun loadBitmap(photoUri: Uri):Bitmap? {
        var image:Bitmap?=null
        try {
            image = if(Build.VERSION.SDK_INT > 27) {
                val source : ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            }else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }
        return image
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK)
            when(requestCode) {
                REQ_CAMERA -> {
                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri)
                        binding.imagePreview.setImageBitmap(bitmap)

                        realUri =null
                    }

                }
                REQ_STORAGE -> {
                    //갤러리에서 가져온 이미지데이터를 IMAGEPREVIEW에 할당
                    //(data의 data속성으로 해당 이미지의 uri가 전달)
                    data?.data?.let { uri ->
                        binding.imagePreview.setImageURI(uri)
                    }
                }
            }
    }


}