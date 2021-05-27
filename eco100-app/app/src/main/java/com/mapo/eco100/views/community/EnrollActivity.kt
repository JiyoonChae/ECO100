package com.mapo.eco100.views.community

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.mapo.eco100.views.login.KakaoLoginUtils
import com.mapo.eco100.R
import com.mapo.eco100.config.PICK_PHOTO
import com.mapo.eco100.config.REQUEST_PERMISSION
import com.mapo.eco100.databinding.ActivityEnrollBinding
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.entity.board.BoardWriteForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.entity.board.BoardModifyForm
import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.views.network.NoConnectedDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.lang.Exception

class EnrollActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnrollBinding
    private lateinit var myImageDir: File
    private var boardBeforeEdit: BoardReadForm? = null
    private var isEditing = false
    private var isImageDeleted = false

    private var fileLocation = ""

    private lateinit var boardService : BoardService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        boardService = NetworkSettings.retrofit.build().create(BoardService::class.java)

        //글 수정일 경우 넘어올 데이터
        boardBeforeEdit = intent.getSerializableExtra("board_data") as BoardReadForm?
        boardBeforeEdit?.let {
            isEditing = true
            binding.enrollTitle.setText(it.title)
            binding.enrollContents.setText(it.contents)
            it.imageUrl?.let { originImageUrl ->
                Glide.with(this@EnrollActivity)
                    .load(originImageUrl.toUri())
                    .into(binding.enrollImage)
                binding.enrollImageText.visibility = View.GONE
            }
        }


        binding.enrollImage.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    //권한이 부여되었을 때 갤러리에서 사진을 선택함
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_PERMISSION
                    )
                }
            }
        }
        binding.send.text = if(isEditing) "수정" else "등록"
        binding.send.setOnClickListener {
            if (!NetworkSettings.isNetworkAvailable(this)) {
                val dialog = NoConnectedDialog(this)
                dialog.show()
            } else if (!KakaoLoginUtils(this).isLogin()) {
                KakaoLoginUtils(this).login()
            } else {
                val userId = this.getSharedPreferences("login", Context.MODE_PRIVATE).getLong("userId",-1)
                Log.d("userId",userId.toString())
                if (isEditing) {//글 수정일 경우
                    if (fileLocation == "") {//사진 추가를 안했을 경우
                        val board = BoardModifyForm(userId,
                            binding.enrollTitle.text.toString(),
                            binding.enrollContents.text.toString(),
                            isImageDeleted
                        )
                        boardService.modify(board).enqueue(object : Callback<Void> {
                            override fun onResponse(
                                call: Call<Void>,
                                response: Response<Void>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(this@EnrollActivity,"글 수정 성공",Toast.LENGTH_SHORT).show()
                                    setResult(RESULT_OK)
                                    finish()
                                } else {
                                    Toast.makeText(this@EnrollActivity,response.body().toString(),Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("EnrollActivity", "실패 : $t")
                            }
                        })
                    } else {
                        //새로 사진 추가하여 글 수정한 경우
                        fileUploadAsync(userId)
                    }
                } else {//글 등록
                    if (fileLocation == "") {
                        sendWithoutImage(userId)
                    } else {
                        fileUploadAsync(userId)
                    }
                }
            }
        }
        binding.imageDelete.setOnClickListener {
            val titleText = if (isEditing) "이미 등록한 사진을 삭제하시겠습니까?" else "불러온 사진을 취소하시겠습니까?"
            AlertDialog.Builder(this@EnrollActivity)
                .setTitle(titleText)
                .setPositiveButton("확인") { _, _ ->
                    if(isEditing && boardBeforeEdit!!.imageUrl != null) {
                        isImageDeleted = true
                    }
                    fileLocation = ""
                    binding.enrollImage.setImageResource(R.drawable.icon_add_picture)
                    binding.enrollImageText.visibility = View.VISIBLE
                }
                .setNegativeButton("취소") { _, _ -> }
                .create()
                .show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //권한이 부여되었을 때 호출되는 함수
        when (requestCode) {
            REQUEST_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigatePhotos()
                } else {
                    Toast.makeText(this, "권한을 거부하였습니다", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                // pass
            }
        }
    }

    private fun navigatePhotos() {
        //SAF(Storage Access Framework)
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK || resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "사진을 가져오지 않았습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        when (requestCode) {
            PICK_PHOTO -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    binding.enrollImage.setImageURI(selectedImageUri)
                    if(findImageFileNameFromUri(selectedImageUri)) {
                        Log.d("PICK_FROM_GALLERY","갤러리에서 절대주소 Pick 성공")
                    } else {
                        Log.d("PICK_FROM_GALLERY","갤러리에서 절대주소 Pick 실패")
                    }
                } else {
                    val extras = data?.extras
                    val bitmapfile = extras!!["data"] as Bitmap?
                    if (tempSavedBitmapFile(bitmapfile)) {
                        Log.e("PICK_FROM_GALLERY", "갤러리에서 Uri값이 없어 실제 파일로 저장 성공")
                    } else {
                        Log.e("PICK_FROM_GALLERY", "갤러리에서 Uri값이 없어 실제 파일로 저장 실패")
                    }
                }
                binding.enrollImageText.visibility = View.GONE
            }
            else -> {

            }
        }
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다")
            .setMessage("사진을 불러오기 위해 권한이 필요함")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(
                    //반드시 array가 들어가야 함
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION
                )
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
            .show()
    }

    private fun sendWithoutImage(userId:Long) {
        val board = BoardWriteForm(userId,
            binding.enrollTitle.text.toString(),
            binding.enrollContents.text.toString()
        )

        boardService.write(board).enqueue(object : Callback<Boards> {
            override fun onResponse(
                call: Call<Boards>,
                response: Response<Boards>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(this@EnrollActivity,"글 등록 성공",Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@EnrollActivity,response.errorBody().toString(),Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boards>, t: Throwable) {
                Toast.makeText(this@EnrollActivity, "연결 실패", Toast.LENGTH_SHORT).show()
                Log.d("EnrollActivity", "실패 : $t")
//                        call.let {
//                            if (t != null) {
//                                error(t)
//                            }
//                        }
            }
        })
    }

    private fun findImageFileNameFromUri(tempUri:Uri) : Boolean {
        var flag = false
        val IMAGE_DB_COLUMN = arrayOf(MediaStore.Images.ImageColumns.DATA)
        var cursor : Cursor? = null
        try {
            val imagePK = ContentUris.parseId(tempUri).toString()
            cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_DB_COLUMN,
                MediaStore.Images.Media._ID + "=?", arrayOf(imagePK), null,null)
            if(cursor!!.count > 0) {
                cursor.moveToFirst()
                fileLocation = cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                )
                flag = true
            }
        } catch (sqle: SQLiteException) {
            Log.d("findImage...",sqle.toString(),sqle)
        } finally {
            cursor?.close()
        }
        return flag
    }

    private fun tempSavedBitmapFile(tempBitmap: Bitmap?): Boolean {
        val flag = false
        try {
            val tempName = "upload_" + System.currentTimeMillis() / 1000
            val fileSuffix = ".jpg"
            //임시파일을 실행한다.(현재앱이 종료되면 파일자동삭제)
            val tempFile = File.createTempFile(
                tempName,  // prefix
                fileSuffix,  // suffix
                myImageDir // directory
            )
            val bitmapStream = FileOutputStream(tempFile)
            tempBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bitmapStream)
            bitmapStream.close()
            fileLocation = tempFile.absolutePath
        } catch (i: IOException) {
            Log.e("저장중 문제발생", i.toString(), i)
        }
        return flag
    }

    private fun fileUploadAsync(userId: Long) {
        binding.enrollProgressBar.visibility = View.VISIBLE

        Thread {
            val uploadFile = File(fileLocation)
            var response: okhttp3.Response? = null
            try {

                val fileUploadBody:RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "image",uploadFile.name,
                        RequestBody.create("image/*".toMediaTypeOrNull(), uploadFile)
                    )
                    .addFormDataPart("userId",userId.toString())
                    .addFormDataPart("title",binding.enrollTitle.text.toString())
                    .addFormDataPart("contents",binding.enrollContents.text.toString())
                    .build()

                val additionalUrl : String = if(isEditing) "/board/update/image" else "/board/create/image"
                response = if (isEditing) {
                    val putImageRequest = Request.Builder()
                        .addHeader("Authorization", "Bearer ")
                        .url(NetworkSettings.baseUrl +additionalUrl)
                        .put(fileUploadBody)
                        .build()
                    NetworkSettings.imageClient.newCall(
                        putImageRequest).execute()
                } else {
                    NetworkSettings.imageClient.newCall(
                        NetworkSettings.imageRequest(additionalUrl,fileUploadBody)).execute()
                }
                if (response.isSuccessful) {
//                    val board = Gson().fromJson(response.body!!.string(), Boards::class.java)
//                    val intent = Intent()
//                    intent.putExtra("created_board", board)
                    runOnUiThread {
                        binding.enrollProgressBar.visibility = View.GONE
                    }
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this,"전송 실패",Toast.LENGTH_SHORT).show()
                    //전송 실패
                }
            } catch (e:Exception) {
                Log.e("UploadError",e.toString())
            } finally {
                response?.close()
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (fileLocation.isNotEmpty()) {
            outState.putString("fileLocation", fileLocation)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (!savedInstanceState.isEmpty) {
            fileLocation = savedInstanceState.getString("fileLocation").toString()
        }
    }

    public override fun onResume() {
        super.onResume()

        val currentAppPackage = packageName
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            myImageDir = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                File(Environment.getExternalStorageDirectory().absolutePath, currentAppPackage)
            } else {
                File(Environment.getExternalStorageDirectory().absolutePath, "uploadImage")
            }
            //checkSDCardPermission()
        } else {
            myImageDir = File(
                Environment.getExternalStorageDirectory().absolutePath, "uploadImage"
            )
        }
        if (myImageDir.mkdirs()) {
            Toast.makeText(application, " 저장할 디렉토리가 생성 됨", Toast.LENGTH_SHORT).show()
        }
    }
    /*
    private fun checkSDCardPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                }
                requestPermissions(
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    MY_PERMISSION_REQUEST_STORAGE
                )
            } else {
                //사용자가 허락함
            }
        }
    }

     */
}