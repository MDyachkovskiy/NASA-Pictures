package com.test.application.asteroids

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.test.application.asteroids.databinding.FragmentAsteroidsListBinding
import com.test.application.core.domain.asteroids.Asteroid
import com.test.application.core.domain.asteroids.AsteroidsList
import com.test.application.core.navigation.FragmentAdder
import com.test.application.core.utils.getTheDateInFormat
import com.test.application.core.view.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class AsteroidsListFragment : BaseFragment<AsteroidsList, FragmentAsteroidsListBinding>(
    FragmentAsteroidsListBinding::inflate
) {

    private val viewModel: AsteroidsListViewModel by viewModel()

    override fun findProgressBar(): FrameLayout {
        return binding.progressBar
    }

    override fun setupData(data: AsteroidsList) {
        val adapter = AsteroidsListAdapter(data, binding.asteroidsList)
        adapter.onAsteroidClick = { asteroid ->
            openAsteroidDetails(asteroid)
        }
        binding.asteroidsList.adapter = adapter
    }

    private fun openAsteroidDetails(asteroid: Asteroid) {
        val fragment = AsteroidDetailsFragment.newInstance(Bundle())
        fragment.arguments = Bundle().apply {
            putParcelable(KEY_BUNDLE_ASTEROID, asteroid)
        }
        (activity as? FragmentAdder)?.replaceAsteroidFragment(fragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
    }

    private fun initViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { appState ->
                    renderData(appState)
                }
            }
        }
        viewModel.getAsteroidsList(getTheDateInFormat(0))
    }
}