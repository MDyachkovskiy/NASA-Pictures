package com.gb_materialdesign.ui.main.asteroids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentAsteroidDetailsBinding
import com.gb_materialdesign.model.asteroids.Asteroid
import com.gb_materialdesign.utils.KEY_BUNDLE_ASTEROID

class AsteroidDetailsFragment : Fragment() {

    private var _binding: FragmentAsteroidDetailsBinding? = null
    private val binding get() = _binding!!

    private var isInfoShowed = false

    companion object{
        fun newInstance(bundle: Bundle): AsteroidDetailsFragment {
            val fragment = AsteroidDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAsteroidDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val asteroid = arguments?.getParcelable(KEY_BUNDLE_ASTEROID)?: Asteroid()
        displayAsteroidDetails(asteroid)

        binding.tap.setOnClickListener {
            informationAnimation()
        }
    }

    private fun informationAnimation() {
        isInfoShowed = !isInfoShowed
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.asteroidDetail)

        val changeBounds = ChangeBounds()
        changeBounds.duration = 1000
        changeBounds.interpolator = AnticipateOvershootInterpolator(5.0f)
        TransitionManager.beginDelayedTransition(binding.root)

        if (isInfoShowed) {
            constraintSet.connect(R.id.asteroid_name,ConstraintSet.RIGHT,
                R.id.asteroid_detail, ConstraintSet.RIGHT)

        } else {
            constraintSet.connect(R.id.asteroid_name,ConstraintSet.RIGHT,
                R.id.asteroid_detail, ConstraintSet.LEFT)
        }
        constraintSet.applyTo(binding.asteroidDetail)
    }

    private fun displayAsteroidDetails(asteroid: Asteroid) {

        with(binding) {
            asteroidName.text = asteroid.name
            minEstimatedDiameter.text = asteroid.estimatedDiameter
                .kilometers.estimatedDiameterMin.toString()

            maxEstimatedDiameter.text = asteroid.estimatedDiameter
                .kilometers.estimatedDiameterMax.toString()

            asteroidVelocity.text = asteroid.closeApproachData[0].relativeVelocity
                .kilometersPerSecond

            missDistance.text = asteroid.closeApproachData[0].missDistance.kilometers

            if(asteroid.isPotentiallyHazardousAsteroid) {
                potentiallyHazardous.text = "Представляет опасность"
                context?.let {
                    potentiallyHazardous.setTextColor(ContextCompat
                        .getColor(it, R.color.colorPrimaryDarkMarsTheme))
                }
            } else {
                potentiallyHazardous.text = "Потенциально не опасный"
                context?.let {
                    potentiallyHazardous.setTextColor(
                        ContextCompat
                            .getColor(it, R.color.teal_700)
                    )
                }
            }

            if(asteroid.isSentryObject) {
                sentryObject.text = "Отслеживается"
                context?.let {
                    sentryObject.setTextColor(ContextCompat
                        .getColor(it, R.color.colorPrimaryDarkMarsTheme))
                }
            } else {
                sentryObject.text = "Не отслеживается"
                context?.let {
                    sentryObject.setTextColor(
                        ContextCompat
                            .getColor(it, R.color.teal_700)
                    )
                }
            }
            TransitionManager.beginDelayedTransition(binding.root)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}