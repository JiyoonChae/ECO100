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
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.views.ecobox.*


class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!
    private lateinit var faqRecyclerView: RecyclerView

    var faqSearchList = arrayOf<String>()

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

    var data_answer = arrayOf(
            "물기를 닦고 난 종이로 만든 핸드 타월은 종이류 수거함으로 배출합니다.\n※ 음식물 등 이물질이 묻어 있는 경우에는 종량제봉투로 배출합니다.",
            "깨진 유리는 수거작업 시 안전사고 방지를 위하여 신문지 등으로 감싼 후 종량제 봉투로 배출합니다.\n단, 깨진 유리의 양이 많을 경우 \n특수규격마대(불연성 전용봉투)로 배출합니다.",
            "페트병과 다른 재질인 경우에는 페트병과 분리하여 각각 분리배출합니다.\n라벨이 비닐류이면 페트병에서 제거하여 비닐류 수거함으로 배출하고, 뚜껑이 페트병과 동일 재질이면 페트병과 함께 페트병 수거함으로 배출합니다.",
            "내용물을 비우고 물로 깨끗이 헹군 후 부착상표 및 박스테이프 등은 제거하고 스티로폼류 수거함으로 배출합니다.",
            "분리배출 표시가 없더라도 깨끗한 비닐류는 재활용이 가능하므로 비닐류 수거함으로 배출합니다.",
            "폐의약품은 약국, 보건소 등으로 배출하면 되며, 수거된 폐의약품은 소각처리 됩니다."
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

    inner class FaqRecyclerAdapter : RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolderClass>() , Filterable {


        // Item의 클릭 상태를 저장할 array 객체
        private val selectedItems: SparseBooleanArray = SparseBooleanArray()

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
            holder.faqAnswerTextView.text = data_answer[position]

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
            return data_question.size
        }

        override fun getFilter(): Filter? {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence): FilterResults {
                    val charString = constraint.toString()
                    if (charString.isEmpty()) {
                        faqSearchList = data_question
                    } else {
                        val filteredList = ArrayList<String>()
                        if (faqSearchList != null) {
                            for (name in faqSearchList) {
                                if (name.toLowerCase().contains(charString.toLowerCase())) {
                                    filteredList.add(name);
                                }
                            }
                        }
                        filteredList
                    }
                    val filterResults = FilterResults()
                    filterResults.values=faqSearchList
                    System.out.println("여기다 이놈아"+filterResults.values.toString())
                    return filterResults
                }

                override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
                    faqSearchList= filterResults?.values as Array<String>
                    notifyDataSetChanged()
                    System.out.println("here")
                }
            }
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



