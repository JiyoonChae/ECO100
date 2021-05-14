package com.mapo.eco100.config

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.mapo.eco100.R
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.FileNotFoundException
import java.io.InputStream

class AppInit : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_app_key).substring(5))

        Thread {
            LocalDataBase.isLoading = true
            lateinit var workbook : HSSFWorkbook
            val inputStream : InputStream
            val assetManager = assets
            val fileSystem : POIFSFileSystem
            try {
                inputStream = assetManager.open("Database.xls")
                fileSystem = POIFSFileSystem(inputStream)
                workbook = HSSFWorkbook(fileSystem)

                val FAQ_Jeju_sheet = workbook.getSheetAt(0)

            } catch (e:FileNotFoundException) {
                Log.e("AppInit","데이터를 불러올 파일이 존재하지 않습니다.")
            } finally {
                workbook.close()
            }


            LocalDataBase.trashBagInfos.apply {

            }
            LocalDataBase.isLoading = false
        }.start()
    }
}