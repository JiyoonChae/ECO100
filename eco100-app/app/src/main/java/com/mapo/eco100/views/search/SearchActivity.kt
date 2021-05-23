package com.mapo.eco100.views.search

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.FAQ_JEJU_list
import com.mapo.eco100.config.LocalDataBase.Companion.search_FAQ_JEJU
import com.mapo.eco100.databinding.ActivitySearchBinding
import java.util.stream.Collectors

class SearchActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySearchBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        binding.searchInput.setOnEditorActionListener{ textView, action, event ->
            var handled = false

            if (action == EditorInfo.IME_ACTION_DONE) {
                // 키보드 내리기
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.searchInput.windowToken, 0)
                handled = true
            }

            handled
        }
        val nameList = FAQ_JEJU_list.stream().map { faq ->
            faq.name
        }.collect(Collectors.toList())

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nameList)
        binding.searchInput.setAdapter(adapter)

        binding.search.setOnClickListener {
            val searchData: Editable = binding.searchInput.text
            val result = search_FAQ_JEJU(searchData)

            manager.hideSoftInputFromWindow(
                currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )

            if (searchData.isEmpty()) {
                Toast.makeText(this, "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show()
            } else {

                if (result?.size != 0) {
                    binding.searchImg.visibility = View.GONE
                    binding.searchTitle.text = "SEARCH : \"$searchData\""

                    val resultText = result!!.stream().map { faq ->
                        faq.details
                    }.collect(Collectors.toList())
                        .toString()
                    binding.searchInfo.text = resultText.substring(1, resultText.length - 1)
                    binding.searchResultBg.visibility = View.VISIBLE

                } else {
                    binding.searchImg.visibility = View.VISIBLE
                    binding.searchImg.setImageResource(R.drawable.img_search_no_data)
                    binding.searchTitle.text = "검색되는 정보가 없습니다 :)"
                    binding.searchResultBg.visibility = View.GONE
                    binding.searchInfo.text = ""
                }
            }
        }

    }
}
