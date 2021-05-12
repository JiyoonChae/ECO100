package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mapo.eco100.R
import com.mapo.eco100.databinding.EcoboxFragmentRecycleGuideBinding
import com.mapo.eco100.databinding.FragmentEcoboxBinding
import com.mapo.eco100.views.ecobox.*

class EcoBoxRecycleGuideFragment : Fragment() {

    private var _binding: EcoboxFragmentRecycleGuideBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = EcoboxFragmentRecycleGuideBinding.inflate(inflater, container, false)

        binding.btnPaper.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RecycleGuidePaperActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnGlass.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RecycleGuideGlassActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnPlastic.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RecycleGuidePlasticActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnCan.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RecycleGuideCanActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnVinyl.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RecycleGuideVinylActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnStyrofoam.setOnClickListener {
            activity?.let{
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