package com.mapo.eco100.views.login

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.mapo.eco100.databinding.ActivityNoLoginDialogBinding

class NoLoginDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: ActivityNoLoginDialogBinding
    private val _context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoLoginDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            KakaoLoginUtils(_context).login()
            dismiss()
        }
    }
}