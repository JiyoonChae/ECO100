package com.mapo.eco100.views.ecobox

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.mapo.eco100.databinding.FragmentContentWebViewBinding

class ContentWebViewFragment : Fragment() {

    private var _binding: FragmentContentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContentWebViewBinding.inflate(inflater, container, false)

        val url = arguments?.getString("webUrl")

        // 웹 뷰 기본 설정
        binding.contentWebView.apply {
            settings.javaScriptEnabled = true // 웹페이지 자바스크립트 허용 여부
            settings.setSupportZoom(true)   // 화면 줌 허용 여부
            settings.builtInZoomControls = true // 화면 확대 축소 허용 여부
            webViewClient = WebViewClient() // 새창 띄우기 허용 여부

        }
        .loadUrl("$url") // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

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