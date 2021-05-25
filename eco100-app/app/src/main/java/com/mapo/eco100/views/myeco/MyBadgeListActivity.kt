package com.mapo.eco100.views.myeco

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.databinding.ActivityMyBadgeListBinding
import com.mapo.eco100.databinding.FragmentBadgeDialogBinding

class MyBadgeListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMyBadgeListBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBadgeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomDialog = BadgeDialogFragment()

        val bottomDialogBinding = FragmentBadgeDialogBinding.inflate(layoutInflater)

        binding.btn1.setOnClickListener {


            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn2.setOnClickListener {


            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn3.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn4.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn5.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn6.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn7.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn8.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn9.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn10.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn11.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }

        binding.btn12.setOnClickListener {
            bottomDialog.show(supportFragmentManager, bottomDialog.tag)
        }


    }

    fun setData(fragment: Fragment, title:String): Fragment {
        val bundle =Bundle()
        bundle.putString("title", title)
        fragment.arguments = bundle
        return fragment
    }

}

