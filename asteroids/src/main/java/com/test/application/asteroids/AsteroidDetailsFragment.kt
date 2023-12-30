package com.test.application.asteroids

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import com.test.application.asteroids.databinding.FragmentAsteroidDetailsBinding
import com.test.application.core.domain.asteroids.Asteroid

class AsteroidDetailsFragment : Fragment() {

    private var _binding: FragmentAsteroidDetailsBinding? = null
    private val binding get() = _binding!!

    private var isInfoShowed = false

    companion object{
        fun newInstance(bundle: Bundle) = AsteroidDetailsFragment().apply { arguments = bundle }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("@@@", "AsteroidDetailsFragment onCreateView: started")
        _binding = FragmentAsteroidDetailsBinding.inflate(inflater, container, false)
        Log.d("@@@", "AsteroidDetailsFragment onCreateView: view created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val asteroid = arguments?.getParcelable(KEY_BUNDLE_ASTEROID)?: Asteroid()
        displayAsteroidDetails(asteroid)

        binding.tap.setOnClickListener { informationAnimation() }
    }

    private fun informationAnimation() {
        isInfoShowed = !isInfoShowed
        updateConstraintsForInformation(isInfoShowed)
        animateConstrainChange()
    }

    private fun animateConstrainChange() {
        val changeBounds = ChangeBounds().apply {
            duration = 1000
            interpolator = AnticipateOvershootInterpolator(5.0f)
        }
        TransitionManager.beginDelayedTransition(binding.root, changeBounds)
    }

    private fun updateConstraintsForInformation(isInfoShowed: Boolean) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.asteroidDetail)
        val constraintSide = if(isInfoShowed) ConstraintSet.RIGHT else ConstraintSet.LEFT
        constraintSet
            .connect(R.id.asteroid_name, ConstraintSet.RIGHT, R.id.asteroid_detail, constraintSide)
        constraintSet.applyTo(binding.asteroidDetail)
    }

    private fun displayAsteroidDetails(asteroid: Asteroid) {
        Log.d("@@@", "AsteroidDetailsFragment displayAsteroidDetails")
        with(binding) {
            asteroidName.text = asteroid.name
            minEstimatedDiameter.text = asteroid.estimatedDiameter
                .kilometers.estimatedDiameterMin.toString()

            maxEstimatedDiameter.text = asteroid.estimatedDiameter
                .kilometers.estimatedDiameterMax.toString()

            asteroidVelocity.text = asteroid.closeApproachData[0].relativeVelocity
                .kilometersPerSecond

            missDistance.text = asteroid.closeApproachData[0].missDistance.kilometers

            setTextWithColor(potentiallyHazardous,
                if(asteroid.isPotentiallyHazardousAsteroid) R.string.potentially_hazardous_asteroid
                    else R.string.potentially_non_hazardous_asteroid,
                if(asteroid.isPotentiallyHazardousAsteroid) R.color.alert_text_color
                    else R.color.non_alert_text_color)

            setTextWithColor(sentryObject,
                if(asteroid.isSentryObject) R.string.sentry_asteroid
                else R.string.non_sentry_asteroid,
                if(asteroid.isSentryObject) R.color.alert_text_color
                else R.color.non_alert_text_color)
        }
    }

    private fun setTextWithColor(textView: TextView, stringId: Int, colorId: Int) {
        textView.text = getString(stringId)
        textView.setTextColor(ContextCompat.getColor(requireContext(), colorId))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}