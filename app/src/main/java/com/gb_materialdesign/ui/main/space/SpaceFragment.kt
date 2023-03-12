package com.gb_materialdesign.ui.main.space

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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

    private val duration = 1000L

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

            menuAnimation()

        }
    }

    private fun menuAnimation() {
        isMenuDisplayed = !isMenuDisplayed
        if(isMenuDisplayed) {
            ObjectAnimator.ofFloat(binding.plusFab, View.ROTATION, 0f, 360f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.solarFlareOption, View.TRANSLATION_Y,  -225f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.geomagneticStormOption, View.TRANSLATION_Y, -315f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.radiationBeltOption, View.TRANSLATION_Y, -405f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0.5f)
                .setDuration(duration)
                .start()

            binding.solarFlareOption.animate().alpha(1f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.solarFlareOption.isClickable = true
                    }
                }
            )

            binding.geomagneticStormOption.animate().alpha(1f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.geomagneticStormOption.isClickable = true
                    }
                }
            )

            binding.radiationBeltOption.animate().alpha(1f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.radiationBeltOption.isClickable = true
                    }
                }
            )


        } else {
            ObjectAnimator.ofFloat(binding.plusFab, View.ROTATION, 0f, -360f)
                .setDuration(1000)
                .start()
            ObjectAnimator.ofFloat(binding.solarFlareOption, View.TRANSLATION_Y, 0f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.geomagneticStormOption, View.TRANSLATION_Y, 0f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.radiationBeltOption, View.TRANSLATION_Y, 0f)
                .setDuration(duration)
                .start()
            ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, 0f)
                .setDuration(duration)
                .start()

            binding.solarFlareOption.animate().alpha(0f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.solarFlareOption.isClickable = false
                    }
                }
            )

            binding.geomagneticStormOption.animate().alpha(0f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.geomagneticStormOption.isClickable = false
                    }
                }
            )

            binding.radiationBeltOption.animate().alpha(0f).setDuration(duration).setListener(
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.radiationBeltOption.isClickable = false
                    }
                }
            )
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}