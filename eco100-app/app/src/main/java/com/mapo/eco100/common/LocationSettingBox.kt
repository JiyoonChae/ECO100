package com.mapo.eco100.common

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class LocationSettingBox(private val owner: Activity) : DialogFragment() {
    companion object {
        fun newInstance(currentActivity: Activity): LocationSettingBox {
            return LocationSettingBox(currentActivity)
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(owner)
            .setMessage("단말기의 위치설정이 필요합니다")
            .setPositiveButton("확인") { _ , _ ->
                val target = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                target.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(target)
            }.create()
    }
}