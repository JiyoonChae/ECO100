package com.mapo.eco100.views.network

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.mapo.eco100.databinding.ActivityNoConnectedDialogBinding
import com.mapo.eco100.entity.board.Boards

class NoConnectedDialog(
    private val owner: Activity,
    val NoInternetConnectedCallback: (context: Context, owner: Activity) -> Unit
) : Dialog(owner) {
    private lateinit var binding :  ActivityNoConnectedDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoConnectedDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.okBtn.setOnClickListener {
            NoInternetConnectedCallback(context,owner)
            dismiss()
        }
    }
}