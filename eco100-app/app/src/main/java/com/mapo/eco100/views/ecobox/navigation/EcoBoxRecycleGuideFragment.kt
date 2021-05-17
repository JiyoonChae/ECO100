package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding
import com.mapo.eco100.views.ecobox.*


class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqRecyclerView: RecyclerView

    var data_category = arrayOf(
            "종이류", "유리병", "페트병", "스티로폼류", "비닐류", "기타"
    )

    var data_question = arrayOf(
            "핸드 타월은 어떻게 배출하나요?",
            "깨진 유리는 어떻게 배출하나요?",
            "페트병의 라벨과 뚜껑은 어떻게 배출하나요?",
            "이물질이 묻어 있는 스티로폼은 재활용이 가능한가요?",
            "재활용 표시가 없는 1회용 봉투, 에어캡(뽁뽁이), 세탁소 비닐 등도 재활용이 가능한가요?",
            "감기 시럽, 소화제, 병원 처방 받은 가루약 등 의약품은 어떻게 배출하나요?"
    )


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentRecycleGuideBinding.inflate(inflater, container, false)
        binding.searchIc.setColorFilter(Color.WHITE)
        val adapter = FaqRecyclerAdapter()

        faqRecyclerView = binding.ecoboxFaqRecyclerview
        faqRecyclerView.adapter = adapter
        faqRecyclerView.layoutManager = LinearLayoutManager(context)


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

    inner class FaqRecyclerAdapter : RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolderClass>() {


        // Item의 클릭 상태를 저장할 array 객체
        private val selectedItems: SparseBooleanArray = SparseBooleanArray()

        // 직전에 클릭됐던 Item의 position
        private var prePosition = -1
        var isExpanded: Boolean = false


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolderClass {
            //항목으로 사용할 view객체 생성.
            val itemView = layoutInflater.inflate(R.layout.row_ecobox_faq, null)
            val holder = FaqViewHolderClass(itemView)

            return holder
        }


        override fun onBindViewHolder(holder: FaqViewHolderClass, position: Int) {

            holder.qmarkImageView.setImageResource(R.drawable.ic_ecobox_q_mark)
            holder.faqCategoryTextView.text = data_category[position]
            holder.faqQuestionTextView.text = data_question[position]
            holder.faqArrowDownBtn.setImageResource(R.drawable.ic_faq_arrow_down)


            holder.itemView.setOnClickListener {

                if (selectedItems.get(position)) { // VISIBLE -> INVISIBLE
                    selectedItems.delete(position)

                    holder.layoutExpand.visibility = View.GONE
                } else {
                    // INVISIBLE -> VISIBLE
                    selectedItems.put(position, true)
                    holder.layoutExpand.visibility = View.VISIBLE
                }
            }

        }


        override fun getItemCount(): Int {
            return data_question.size
        }

        inner class FaqViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val qmarkImageView = itemView.findViewById<ImageView>(R.id.qmarkImageView)
            val faqCategoryTextView = itemView.findViewById<TextView>(R.id.faqCategoryTextView)
            val faqQuestionTextView = itemView.findViewById<TextView>(R.id.faqQuestionTextView)
            val faqArrowDownBtn = itemView.findViewById<ImageButton>(R.id.faqArrowDownBtn)
            val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
            val faqAnswerTextView = itemView.findViewById<TextView>(R.id.faqAnswerTextView)

        }

    }

}



