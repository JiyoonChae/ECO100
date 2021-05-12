package com.mapo.eco100.views.network

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityNoConnectedDialogBinding
import com.mapo.eco100.views.MainActivity

class NoConnectedDialog(
    private val owner: MainActivity
) : Dialog(owner) {
    private lateinit var binding :  ActivityNoConnectedDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoConnectedDialogBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)

        binding.okBtn.setOnClickListener {
            owner.NoInternetConnectedCallback()
            dismiss()
        }
    }
}