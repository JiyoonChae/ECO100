package com.mapo.eco100.common

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

abstract class BaseActivity : AppCompatActivity() {
    abstract fun permissionGranted(requestCode: Int)
    abstract fun permissionDenied(requestCode: Int)

    fun requirePermissions(permissions:Array<String>, requestCode: Int){
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            permissionGranted(requestCode)
        }else{
            //권한 체크를 해야하는 버전이면 이 블록 안에서 권한이 모드 승인된 것을 확인
            val isAllPermissionsGranted = permissions.all {
                //permissions에는 권한 배열이 들어있는데, all메서드를 사용하면 모든 값을 체크할 수 있음.
                //권한이 모두 승인되었는지 여부를 변수에 저장
                checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
            }

            if(isAllPermissionsGranted) {
                permissionGranted(requestCode)
            }else {
                //사용자에 권한 승인 요청
                ActivityCompat.requestPermissions(this, permissions, requestCode)
            }


        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }){
            permissionGranted(requestCode)
        }else{
            permissionDenied(requestCode)
        }
    }
}