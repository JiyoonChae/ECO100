@file:Suppress("DEPRECATION")

package com.mapo.eco100.views.community


import android.app.Activity
import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.ActivityWriteChallengeBinding
import com.mapo.eco100.databinding.RowChallengeBinding
import com.mapo.eco100.entity.challenge.Challenge
import com.mapo.eco100.entity.challenge.ChallengeList
import com.mapo.eco100.entity.challenge.ChallengePost
import com.mapo.eco100.service.ChallengeService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

class WriteChallenge : BaseActivity() {
    val PERM_STORAGE = 99 //외부 저장소 권한 처리
    val PERM_CAMERA = 100 //카메라 권한 처리
    val REQ_CAMERA = 101 //카메라 촬영 요청
    val REQ_STORAGE = 102 //갤러리 요청
    var realUri: Uri? = null //이미지 uri가져오기
    var filePath: String =""
    private var challenge: Challenge? = null
    private val maxLimit = 200//글자 제한 설정
    private lateinit var dialog: Dialog

    val binding by lazy { ActivityWriteChallengeBinding.inflate(layoutInflater) }
    val binding2 by lazy { RowChallengeBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        challenge = intent.getSerializableExtra("item") as Challenge
        challenge?.let {
            binding.textView.text = it.subject
            it.imageUrl?.let { url_string ->
                Glide.with(this@WriteChallenge)
                    .load(url_string.toUri())
                    .into(binding.iconChallenge)
            }
        }

        //외부 저장소 권한 요청
        requirePermissions(
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERM_STORAGE
        )

        //글자 제한 설정
        val editText = binding.challengeWriteContent
        val textCount = binding.textCount
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editText.isFocusable && s.toString() != "") {
                    val string: String = s.toString()
                    val len = string.length
                    if (len > maxLimit) {
                        editText.setText(string.substring(0, maxLimit))
                        editText.setSelection(maxLimit)
                    } else {
                        textCount.text = "($len/$maxLimit)"
                    }
                } else {
                    textCount.text = "( 0 / $maxLimit )"

                }


            }
        })

        //글쓰기 완료
        binding.challengeFinish.setOnClickListener {
            Log.d("chall", "버튼 눌림")
            if (filePath == "") {
                // dialog
                dialog = Dialog(binding.root.context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.popup_warning_challenge)
                showDialog()
            } else {
                uploadPost()
            }
        }

        //이미지 삭제
        binding.buttonDelete.setOnClickListener {
            realUri = null
            filePath = ""
            val delimg= binding.challengeWriteImage
            Glide.with(delimg).load(R.drawable.img_ch_write2).fitCenter().into(delimg)

        }

    }


    //저장소 권한 요청이 승인되었을 경우
    override fun permissionGranted(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> setViews()
            PERM_CAMERA -> openCamera()
        }
    }

    //저장소 권한 요청에 대한 승인 거부되었을 경우
    override fun permissionDenied(requestCode: Int) {
        when (requestCode) {
            PERM_STORAGE -> {
                Toast.makeText(baseContext, "외부저장소 권한을 승인해야 사용가능합니다", Toast.LENGTH_LONG).show()
                // finish()
            }
            PERM_CAMERA -> {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 사용할 수 있습니다", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setViews() {
        //버튼 클릭 시 카메라 권한을 요청하는 코드
        binding.buttonCamera.setOnClickListener {
            requirePermissions(arrayOf(android.Manifest.permission.CAMERA), PERM_CAMERA)
        }

        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    //갤러리 호출
    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK) //인텐트 파라미트로 action_pick사용
        intent.type = MediaStore.Images.Media.CONTENT_TYPE //여기서 설정한 데이터를 미디어 스토어에서 불러와 선택가능
        startActivityForResult(intent, REQ_STORAGE)

    }

    //카메라 요청
    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // startActivityForResult(intent, REQ_CAMERA)

        createImageUri(newFileName(), "image/jpg")?.let { uri ->
            realUri = uri  //실제 이미지의 Uri 주소
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQ_CAMERA)
        }
    }

    //이미지를 미디어 스토어에 생성
    fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues() //(파일명, 파일타입 입력)
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    //파일 이름 생성
    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        //파일명 : 연월일_시간.jpg
        return "$filename.jpg"
    }


    //Uri를 사용해서 저장된 이미지 호출
    fun loadBitmap(photoUri: Uri): Bitmap? {
        var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    //결과 처리 메서드 : realUri에 값이 있는지 확인 후 있으면 loadBitmap메서드를 통해 화면에 세팅.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "사진을 가져오지 않았습니다.", Toast.LENGTH_SHORT).show()
            // dialog
            dialog = Dialog(binding.root.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_warning_challenge)
            showDialog()
            return
        }

        if (resultCode == Activity.RESULT_OK) {
            Log.d("resultCode: ", "$resultCode")
            Log.d("requestCode: ", "$requestCode")
            when (requestCode) {
                REQ_CAMERA -> {
                    /* val uri: Uri = data!!.data!!
                     Log.d("절대", "path :"+ uri)*/

                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri) //uri를 사용해서 미디어스토어에 저장된 이미지를 읽어옴>bitmap으로 변환
                        binding.challengeWriteImage.setImageBitmap(bitmap)
                        filePath = getImageFilePath(uri)
                        Log.d("절대주소", "path :" + filePath)
                        realUri = null
                    }
                }
                REQ_STORAGE -> {
                    //갤러리에서 가져온 이미지데이터를 IMAGEPREVIEW에 할당
                    //(data의 data속성으로 해당 이미지의 uri가 전달)
                    data?.data?.let { uri ->
                        binding.challengeWriteImage.setImageURI(uri)
                        realUri = uri
                        filePath = getImageFilePath(uri)
                        Log.d("절대주소", "path :" + filePath)
                    }

                }
            }
        }


    }


    fun getImageFilePath(contentUri: Uri): String {
        var columnIndex = 0
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, projection, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    fun uploadPost() {
        if (filePath == "") {
            // dialog
            dialog = Dialog(binding.root.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.popup_warning_challenge)
            showDialog()
            return
        }
        Thread {
            val uploadFile = File(filePath)
            var response: okhttp3.Response? = null
            try {
                val fileUploadBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "image",uploadFile.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), uploadFile)
                    )
                    .addFormDataPart("userId", "1")
                    .addFormDataPart("challengeId", challenge!!.challengeId.toString())
                    .addFormDataPart("contents", binding.challengeWriteContent.text.toString())
                    .build()

                response = NetworkSettings.imageClient.newCall(
                    NetworkSettings.imageRequest("/challenge/create", fileUploadBody)
                ).execute()
                if (response.isSuccessful) {
                    Log.d("서버 등록: ", "성공")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Log.d("서버 등록: ", "실패")
                    Log.d("서버 등록: ", response.code.toString())
                   // Toast.makeText(this, "전송 실패", Toast.LENGTH_SHORT).show()
                    //전송 실패
                }

            } catch (e: Exception) {
                Log.e("서버 등록: ", e.toString())
            } finally {
                response?.close()
            }

        }.start()

    }
    /*val service: ChallengeService =
        NetworkSettings.retrofit.build().create(ChallengeService::class.java)

    service.challengeUpload(1.toString(), "21", content, part).enqueue(object : Callback<ChallengePost> {

        override fun onFailure(call: Call<ChallengePost>, t: Throwable) {
            Log.d("챌린지", "챌린지 등록 실패")
        }

        override fun onResponse(call: Call<ChallengePost>, response: Response<ChallengePost>) {
            if (response.isSuccessful) {
                val challengNew = response.body()
                Log.d("챌린지", challengNew!!.challengePostId.toString())
                finish()
            }else{
                Log.d("챌린지","실패")
            }
        }
    })*/
    //}


    //dialog
    private fun showDialog() {
        dialog.show()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val okBtn: AppCompatButton = dialog.findViewById(R.id.challenge_popup_ok)
        okBtn.setOnClickListener {
            dialog.dismiss()
        }
        val cxlBtn: AppCompatButton = dialog.findViewById(R.id.challenge_popup_cancel)
        cxlBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
}