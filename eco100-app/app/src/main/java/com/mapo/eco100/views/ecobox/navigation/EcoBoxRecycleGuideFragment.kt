package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.config.LocalDataBase.Companion.search_FAQ
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.views.ecobox.*


class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqRecyclerView: RecyclerView
    internal var textlength = 0

    //필터링을 위한 변수
    var searchItemlist= arrayListOf<String>()
    var faqSearchList = mutableListOf<FAQ>()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentRecycleGuideBinding.inflate(inflater, container, false)
        binding.searchIc.setColorFilter(Color.WHITE)
        var adapter = FaqRecyclerAdapter()

        faqRecyclerView = binding.ecoboxFaqRecyclerview
        faqRecyclerView.adapter = adapter
        faqRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {}
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                search_FAQ(binding.searchEdit.toString())



//                textlength = binding.searchEdit.text.length
//                faqSearchList.clear()
//                var str_sequence = binding.searchEdit.text.toString()
//                for (i in faqList.indices) {
//                    if (faqList[i].answer.toString().contains(str_sequence) ||
//                            faqList[i].question.toString().contains(str_sequence))
//                        faqSearchList.add(faqList[i])
//                }
//                adapter = FaqRecyclerAdapter(faqSearchList)
//                faqRecyclerView = binding.ecoboxFaqRecyclerview
//                faqRecyclerView.adapter = adapter
//                faqRecyclerView.layoutManager = LinearLayoutManager(context)
            }

        })


        binding.btnPaper.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuidePaperActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnGlass.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideGlassActivity::class.java)
                startActivity(intent)

            }
        }

        binding.btnPlastic.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuidePlasticActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnCan.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideCanActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnVinyl.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideVinylActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnStyrofoam.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideStyrofoamActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root

    }

    companion object {
        fun newInstance(): EcoBoxRecycleGuideFragment {
            return EcoBoxRecycleGuideFragment()
        }
    }


}



