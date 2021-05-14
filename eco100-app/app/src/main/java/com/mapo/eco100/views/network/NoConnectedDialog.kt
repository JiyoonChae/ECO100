package com.mapo.eco100.views.network

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.mapo.eco100.config.NetworkSettings.NoInternetConnectedCallback
import com.mapo.eco100.databinding.ActivityNoConnectedDialogBinding

class NoConnectedDialog(
    context: Context
) : Dialog(context) {
    private lateinit var binding: ActivityNoConnectedDialogBinding
    private val _context = context

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoConnectedDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.okBtn.setOnClickListener {
            NoInternetConnectedCallback(_context)
            dismiss()
        }
    }
}