package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase
import com.mapo.eco100.config.LocalDataBase.Companion.FAQ_list
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.views.ecobox.*


class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqRecyclerView: RecyclerView
    internal var textlength = 0

    //필터링을 위한 변수
//    var searchItemlist= arrayListOf<String>()
    var faqList = FAQ_list
    var faqSearchList = mutableListOf<FAQ>()


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentRecycleGuideBinding.inflate(inflater, container, false)
        binding.searchIc.setColorFilter(Color.WHITE)
        var adapter = FaqRecyclerAdapter(faqList)

        faqRecyclerView = binding.ecoboxFaqRecyclerview
        faqRecyclerView.adapter = adapter
        faqRecyclerView.layoutManager = LinearLayoutManager(context)

        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edit: Editable?) {
            }

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
//                LocalDataBase.search_FAQ(binding.searchEdit.text.toString())

                textlength = binding.searchEdit.text.length
                faqSearchList.clear()
                var str_sequence = binding.searchEdit.text.toString()
                for (i in faqList.indices) {
                    if (faqList[i].answer.toString().contains(str_sequence) ||
                            faqList[i].question.toString().contains(str_sequence))
                        faqSearchList.add(faqList[i])
                }
                adapter = FaqRecyclerAdapter(faqSearchList)
                faqRecyclerView = binding.ecoboxFaqRecyclerview
                faqRecyclerView.adapter = adapter
                faqRecyclerView.layoutManager = LinearLayoutManager(context)
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

    inner class FaqRecyclerAdapter(val ArrayList: MutableList<FAQ>) : RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolderClass>() {

        // Item의 클릭 상태를 저장할 array 객체
        private val selectedItems: SparseBooleanArray = SparseBooleanArray()

        inner class FaqViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val qmarkImageView = itemView.findViewById<ImageView>(R.id.qmarkImageView)
            val faqCategoryTextView = itemView.findViewById<TextView>(R.id.faqCategoryTextView)
            val faqQuestionTextView = itemView.findViewById<TextView>(R.id.faqQuestionTextView)
            val faqArrowDownBtn = itemView.findViewById<ImageButton>(R.id.faqArrowDownBtn)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
            val faqAnswerTextView = itemView.findViewById<TextView>(R.id.faqAnswerTextView)


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolderClass {
            //항목으로 사용할 view객체 생성.
            val itemView = layoutInflater.inflate(R.layout.row_ecobox_faq, null)
            val holder = FaqViewHolderClass(itemView)

            return holder
        }


        override fun onBindViewHolder(holder: FaqViewHolderClass, position: Int) {

            holder.qmarkImageView.setImageResource(R.drawable.ic_ecobox_q_mark)
            holder.faqCategoryTextView.text = FAQ_list[position].category.toString()
            holder.faqQuestionTextView.text = FAQ_list[position].question.toString()
            holder.faqAnswerTextView.text = FAQ_list[position].answer.toString()


            holder.faqArrowDownBtn.setOnClickListener {
                if (selectedItems.get(position)) {
                    // VISIBLE -> INVISIBLE
                    selectedItems.delete(position)
                    holder.layoutExpand.visibility = View.GONE
                    holder.faqArrowDownBtn.setImageResource(R.drawable.ic_faq_arrow_down)
                } else {
                    // INVISIBLE -> VISIBLE
                    selectedItems.put(position, true)
                    holder.layoutExpand.visibility = View.VISIBLE
                    holder.faqArrowDownBtn.setImageResource(R.drawable.ic_faq_arrow_up)
                }

            }

        }


        override fun getItemCount(): Int {
            return FAQ_list.size
        }

    }

}



