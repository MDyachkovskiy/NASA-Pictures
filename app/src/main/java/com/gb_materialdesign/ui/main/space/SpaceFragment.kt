package com.gb_materialdesign.ui.main.space

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentSpaceBinding

import com.gb_materialdesign.ui.main.asteroids.AsteroidsListFragment

class SpaceFragment: Fragment() {

    private var _binding: FragmentSpaceBinding? = null
    private val binding get() = _binding!!

    private var isMenuDisplayed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpaceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        childFragmentManager.beginTransaction()
            .replace(R.id.space_container, AsteroidsListFragment.newInstance())
            .addToBackStack("")
            .commit()

        binding.fab.setOnClickListener {
            isMenuDisplayed = !isMenuDisplayed
            if(isMenuDisplayed) {
                ObjectAnimator.ofFloat(binding.plusFab, View.ROTATION, 0f, 360f)
                    .setDuration(1000)
                    .start()
            } else {
                ObjectAnimator.ofFloat(binding.plusFab, View.ROTATION, 0f, -360f)
                    .setDuration(1000)
                    .start()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}