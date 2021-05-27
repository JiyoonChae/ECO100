package com.mapo.eco100.views.myeco

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.mapo.eco100.R
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.ActivityMainBinding
import com.mapo.eco100.databinding.ActivityMyBadgeListBinding
import com.mapo.eco100.databinding.FragmentBadgeDialogBinding
import com.mapo.eco100.entity.challenge.ChallengeList
import com.mapo.eco100.entity.myeco.MyBadge
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.service.ChallengeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyBadgeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyBadgeListBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyBadgeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var badgeCheck = false
        var name = ""
        var check = ""
        val bottomDialog = BadgeDialogFragment()
        val bundle = Bundle()


        val service: BoardService =
            NetworkSettings.retrofit.build().create(BoardService::class.java)
        service.getMyBadge(1).enqueue(object : Callback<ArrayList<Boolean>> {
            override fun onFailure(call: Call<ArrayList<Boolean>>, t: Throwable) {
                Log.d("서버 연결", " 실패 --------------", null)
            }

            override fun onResponse(
                call: Call<ArrayList<Boolean>>, response: Response<ArrayList<Boolean>>
            ) {
                val data = response.body()
                //val index = 1
                data?.forEachIndexed { index, i ->
                    if (i) {
                        name = "btn" + index
                        badgeCheck = i
                        Log.d("name", "$name")
                        Log.d("badgeCheck", "$badgeCheck")

                    } else {
                        badgeCheck = i
                        name = "btn" + index
                        Log.d("name", "$name")
                        Log.d("badgeCheck", "$badgeCheck")

                    }
                    index.plus(1)

                    when (name) {
                        "btn0" -> {
                            binding.btn1.setImageResource(R.drawable.btn1)
                            binding.myBadge1.setTextColor(R.color.black)

                            binding.btn1.setOnClickListener {
                                bundle.putString("num", "1")
                                bundle.putString("check", check)
                                bottomDialog.arguments = bundle

                                bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                            }
                        }
                        "btn1" -> {
                            if (badgeCheck) {
                                binding.btn2.setImageResource(R.drawable.badge2)
                                binding.myBadge2.setTextColor(R.color.black)

                                binding.btn2.setOnClickListener {
                                    bundle.putString("num", "2")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle

                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }

                            } else {
                                binding.btn2.setImageResource(R.drawable.btn2)

                                binding.btn2.setOnClickListener {
                                    bundle.putString("num", "2")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle

                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }

                            }
                        }
                        "btn2" -> {
                            if (badgeCheck) {
                                binding.btn3.setImageResource(R.drawable.badge3)
                                binding.myBadge3.setTextColor(R.color.black)

                                binding.btn3.setOnClickListener {
                                    bundle.putString("num", "3")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn3.setImageResource(R.drawable.btn3)
                                binding.btn3.setOnClickListener {
                                    bundle.putString("num", "3")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn3" -> {
                            if (badgeCheck) {
                                binding.btn4.setImageResource(R.drawable.badge4)
                                binding.myBadge4.setTextColor(R.color.black)

                                binding.btn4.setOnClickListener {
                                    bundle.putString("num", "4")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }

                            } else {
                                binding.btn4.setImageResource(R.drawable.btn4)

                                binding.btn4.setOnClickListener {
                                    bundle.putString("num", "4")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }

                            }
                        }
                        "btn4" -> {
                            if (badgeCheck) {
                                binding.btn5.setImageResource(R.drawable.badge5)
                                binding.myBadge5.setTextColor(R.color.black)

                                binding.btn5.setOnClickListener {
                                    bundle.putString("num", "5")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn5.setImageResource(R.drawable.btn5)
                                binding.btn5.setOnClickListener {
                                    bundle.putString("num", "5")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn5" -> {
                            if (badgeCheck) {
                                binding.btn6.setImageResource(R.drawable.badge6)
                                binding.myBadge6.setTextColor(R.color.black)

                                binding.btn6.setOnClickListener {
                                    bundle.putString("num", "6")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn6.setImageResource(R.drawable.btn6)

                                binding.btn6.setOnClickListener {
                                    bundle.putString("num", "6")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn6" -> {
                            if (badgeCheck) {
                                binding.btn7.setImageResource(R.drawable.badge7)
                                binding.myBadge7.setTextColor(R.color.black)

                                binding.btn7.setOnClickListener {
                                    bundle.putString("num", "7")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn7.setImageResource(R.drawable.btn7)

                                binding.btn7.setOnClickListener {
                                    bundle.putString("num", "7")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn7" -> {
                            if (badgeCheck) {
                                binding.btn8.setImageResource(R.drawable.badge8)
                                binding.myBadge8.setTextColor(R.color.black)

                                binding.btn8.setOnClickListener {
                                    bundle.putString("num", "8")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn8.setImageResource(R.drawable.btn8)

                                binding.btn8.setOnClickListener {
                                    bundle.putString("num", "8")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn8" -> {
                            if (badgeCheck) {
                                binding.btn9.setImageResource(R.drawable.badge9)
                                binding.myBadge9.setTextColor(R.color.black)

                                binding.btn9.setOnClickListener {
                                    bundle.putString("num", "9")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn9.setImageResource(R.drawable.btn9)

                                binding.btn9.setOnClickListener {
                                    bundle.putString("num", "9")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn9" -> {
                            if (badgeCheck) {
                                binding.btn10.setImageResource(R.drawable.badge10)
                                binding.myBadge10.setTextColor(R.color.black)

                                binding.btn10.setOnClickListener {
                                    bundle.putString("num", "10")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn10.setImageResource(R.drawable.btn10)

                                binding.btn10.setOnClickListener {
                                    bundle.putString("num", "10")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn10" -> {
                            if (badgeCheck) {
                                binding.btn11.setImageResource(R.drawable.badge11)
                                binding.myBadge11.setTextColor(R.color.black)

                                binding.btn11.setOnClickListener {
                                    bundle.putString("num", "11")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn11.setImageResource(R.drawable.btn11)

                                binding.btn11.setOnClickListener {
                                    bundle.putString("num", "11")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }
                        "btn11" -> {
                            if (badgeCheck) {
                                binding.btn12.setImageResource(R.drawable.badge12)
                                binding.myBadge12.setTextColor(R.color.black)

                                binding.btn12.setOnClickListener {
                                    bundle.putString("num", "12")
                                    bundle.putString("check", "true")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            } else {
                                binding.btn12.setImageResource(R.drawable.btn12)

                                binding.btn12.setOnClickListener {
                                    bundle.putString("num", "12")
                                    bundle.putString("check", "false")
                                    bottomDialog.arguments = bundle
                                    bottomDialog.show(supportFragmentManager, bottomDialog.tag)
                                }
                            }
                        }

                    }
                }
            }
        })


    }
}

