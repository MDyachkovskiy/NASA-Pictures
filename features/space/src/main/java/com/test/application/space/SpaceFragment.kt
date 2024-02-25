package com.test.application.space

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.application.space.databinding.FragmentSpaceBinding

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
        binding.fab.setOnClickListener {
            menuAnimation()
        }
    }

    private fun menuAnimation() {
        isMenuDisplayed = !isMenuDisplayed
        animateFabRotation()
        animateOptions()
        animateBackground()
    }

    private fun animateBackground() {
        val alpha = if (isMenuDisplayed) 0.5f else 0f
        ObjectAnimator.ofFloat(binding.transparentBackground, View.ALPHA, alpha)
            .setDuration(1000)
            .start()
    }

    private fun animateOptions() {
        val translationY = if(isMenuDisplayed) -225f else 0f
        val options = listOf(binding.solarFlareOption, binding.geomagneticStormOption, binding.radiationBeltOption)

        options.forEachIndexed { index, option ->
            ObjectAnimator.ofFloat(option, View.TRANSLATION_Y, translationY * (index + 1))
                .setDuration(duration)
                .start()

            option.animate()
                .alpha(if (isMenuDisplayed) 1f else 0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter(){
                    override fun onAnimationEnd(animation: Animator) {
                        option.isClickable = isMenuDisplayed
                    }
                })
        }
    }

    private fun animateFabRotation() {
        val rotation = if(isMenuDisplayed) 360f else -360f
        ObjectAnimator.ofFloat(binding.plusFab, View.ROTATION, 0f, rotation).apply {
            duration = this@SpaceFragment.duration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    binding.plusFab.bringToFront()
                }
            })
        }.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}