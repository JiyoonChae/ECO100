package com.mapo.eco100.config

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.entity.staticmodel.FAQ_JEJU
import com.mapo.eco100.entity.staticmodel.GarbageBagShopInfo
import com.mapo.eco100.entity.staticmodel.ZeroShop
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import java.io.*

class AppInit : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: AppInit? = null
        fun applicationContext(): AppInit {
            return instance as AppInit
        }
    }

    override fun onCreate() {
        super.onCreate()
        //카카오 초기화
        KakaoSdk.init(this, getString(R.string.kakao_app_key).substring(5))
        // 모든 액티비티를 수직 화면으로 셋팅
        setAllActivitySettings()

        LocalDataBase.appInitLoading.value = true
        Thread {

            try {
                val inputStream = assets.open("Database.xls")
                val workbook = HSSFWorkbook(POIFSFileSystem(inputStream))


                val FAQ_Jeju_sheet = workbook.getSheetAt(0)
                var rows = FAQ_Jeju_sheet.physicalNumberOfRows
                for (rowIndex in 1 until rows) {
                    val row = FAQ_Jeju_sheet.getRow(rowIndex)
                    val id = rowIndex
                    val type: String = row.getCell(0).toString()
                    val name: String = row.getCell(1).toString()
                    val details: String = row.getCell(2).toString()
                    val precautions: String? = row.getCell(3)?.toString()
                    LocalDataBase.FAQ_JEJU_list.add(FAQ_JEJU(id, type, name, details, precautions))
                }

                val FAQ_sheet = workbook.getSheetAt(1)
                rows = FAQ_sheet.physicalNumberOfRows
                for (rowIndex in 1 until rows) {
                    val row = FAQ_sheet.getRow(rowIndex)
                    val id = rowIndex
                    val category: String = row.getCell(0).toString()
                    val question: String = row.getCell(1).toString()
                    val answer: String = row.getCell(2).toString()
                    LocalDataBase.FAQ_list.add(FAQ(id, category, question, answer))
                }

                val ZeroShop_sheet = workbook.getSheetAt(2)
                rows = ZeroShop_sheet.physicalNumberOfRows
                for (rowIndex in 1 until rows) {
                    val row = ZeroShop_sheet.getRow(rowIndex)
                    val id = rowIndex
                    val name: String = row.getCell(0).toString()
                    val address: String = row.getCell(1).toString()
                    val phoneNum: String? = row.getCell(2)?.toString()
                    val runningInfo: String? = row.getCell(3)?.toString()
                    val webUrl: String? = row.getCell(4)?.toString()
                    val location: String = row.getCell(5).toString()
                    val latitude: Float = location.split(", ")[0].toFloat()
                    val longitude: Float = location.split(", ")[1].toFloat()
                    val detailInfo: String = row.getCell(6).toString()

                    val baseUrl = "http://rpinas.iptime.org:10122" + "/image/"

                    var imgUrl: String? = baseUrl + "shop/" + name + ".jpg"

                    if (id == 15 || id == 28) {
                        imgUrl = null
                    }

                    var logoUrl: String? = baseUrl + "logo/" + name + ".jpg"

                    if (id == 3 || id == 20 || id == 33 || id == 36 || id == 39 || id == 41) {
                        logoUrl = null
                    }

                    LocalDataBase.zeroShopList.add(
                        ZeroShop(
                            id,
                            name,
                            address,
                            phoneNum,
                            runningInfo,
                            webUrl,
                            latitude,
                            longitude,
                            imgUrl,
                            logoUrl,
                            detailInfo
                        )
                    )
                }

                val geoCoder = Geocoder(this)

                val trashBag_sheet = workbook.getSheetAt(3)
                rows = trashBag_sheet.physicalNumberOfRows
                for (rowIndex in 1 until rows) {
                    val row = trashBag_sheet.getRow(rowIndex)
                    val id = rowIndex
                    val address1 = row.getCell(0)?.toString()
                    val address2 = row.getCell(1)?.toString()
                    val name = row.getCell(2)?.toString()
                    address1?.let {
                        var addressList: MutableList<Address>? = null
                        addressList = geoCoder.getFromLocationName(address1, 10)
                        val address = addressList[0]
                        LocalDataBase.garbageBagShopInfos.add(
                            GarbageBagShopInfo(
                                id,
                                address1,
                                address2,
                                name,
                                address.latitude,
                                address.longitude
                                )
                        )

                    }
                }
            } catch (e: FileNotFoundException) {
                Log.e("AppInit", "데이터를 불러올 파일이 존재하지 않습니다.")
            }
            LocalDataBase.appInitLoading.value = false
        }.start()
    }

    private fun setAllActivitySettings() {

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}
}