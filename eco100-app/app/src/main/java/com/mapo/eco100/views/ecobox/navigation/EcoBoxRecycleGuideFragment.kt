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
import com.mapo.eco100.config.LocalDataBase.Companion.FAQ_list
import com.mapo.eco100.config.LocalDataBase.Companion.search_FAQ
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.views.ecobox.*


class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqRecyclerView: RecyclerView

    var faqList = FAQ_list
    var faqSearchList: MutableList<FAQ> = mutableListOf()
    var adapter = FaqRecyclerAdapter(faqList)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentRecycleGuideBinding.inflate(inflater, container, false)
        binding.searchIc.setColorFilter(Color.WHITE)


        faqRecyclerView = binding.ecoboxFaqRecyclerview
        faqRecyclerView.adapter = adapter
        faqRecyclerView.layoutManager = LinearLayoutManager(context)


        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {}
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {

                faqSearchList.clear()

                var strSequence = binding.searchEdit.text.toString()
                var searchlist = search_FAQ(strSequence)
                if (searchlist != null) {
                    faqSearchList = searchlist
                }
                adapter = FaqRecyclerAdapter(faqSearchList)
                faqRecyclerView.adapter = adapter
                faqRecyclerView.layoutManager = LinearLayoutManager(context)
                adapter.notifyDataSetChanged()
            }

        })


        binding.btnPaper.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",1)
                startActivity(intent)

            }
        }

        binding.btnGlass.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",2)
                startActivity(intent)

            }
        }

        binding.btnPlastic.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",3)
                startActivity(intent)
            }
        }

        binding.btnCan.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",4)
                startActivity(intent)
            }
        }

        binding.btnVinyl.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",5)
                startActivity(intent)
            }
        }

        binding.btnStyrofoam.setOnClickListener {
            activity?.let {
                val intent = Intent(context, RecycleGuideActivity::class.java)
                intent.putExtra("number",6)
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



