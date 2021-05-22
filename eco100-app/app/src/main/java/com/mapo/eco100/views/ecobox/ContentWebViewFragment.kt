package com.mapo.eco100.views.ecobox

import android.location.Address
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.webkit.WebViewClient
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentContentsBinding

import com.mapo.eco100.databinding.FragmentContentWebViewBinding

class ContentWebViewFragment : Fragment() {

    private var _binding: FragmentContentWebViewBinding? = null
    private val binding get() = _binding!!
//    private var url: String? = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContentWebViewBinding.inflate(inflater, container, false)

        // 웹 뷰 기본 설정
        binding.contentWebView.apply {
            settings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
            webViewClient = WebViewClient() // 클릭시 새창 안뜨게
        }
        .loadUrl("http://www.google.com") // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

        initWebView()

        return binding.root
    }

    private fun initWebView() {
        binding.contentWebView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event?.action != KeyEvent.ACTION_DOWN)
                    return true
                if (keyCode == KeyEvent.KEYCODE_BACK) { // 뒤로가기 동작
                    if (binding.contentWebView.canGoBack()) { // 웹뷰가 이전 페이지로 돌아갈 수 있다면
                        binding.contentWebView.goBack() // 이전 페이지로 이동
                    } else {
                        (activity as ContentDetailActivity).onBackPressed() // 아니면 원래 동작 수행 (액티비티 종료)
                    }
                    return true
                }
                return false
            }
        })
    }


    companion object {

        fun newInstance(): ContentWebViewFragment{
            return ContentWebViewFragment()
        }
    }
}