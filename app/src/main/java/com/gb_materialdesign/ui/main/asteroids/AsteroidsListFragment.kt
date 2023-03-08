package com.gb_materialdesign.ui.main.asteroids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb_materialdesign.databinding.FragmentAsteroidsListBinding

class AsteroidsListFragment : Fragment() {

    private var _binding: FragmentAsteroidsListBinding? = null
    private val binding get() = _binding!!

    companion object{
        fun newInstance() = AsteroidsListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}