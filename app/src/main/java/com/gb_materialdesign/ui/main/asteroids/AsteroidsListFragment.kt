package com.gb_materialdesign.ui.main.asteroids

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gb_materialdesign.adapters.AsteroidsListAdapter
import com.gb_materialdesign.databinding.FragmentAsteroidsListBinding
import com.gb_materialdesign.ui.main.appState.AppStateRenderer
import com.test.application.core.utils.getTheDateInFormat

class AsteroidsListFragment : Fragment() {

    private var _binding: FragmentAsteroidsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentView: View

    private val viewModel: AsteroidsListViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(AsteroidsListViewModel::class.java)
    }

    private val dataRenderer by lazy {
        AppStateRenderer(parentView) {viewModel.getAsteroidsList(
            getTheDateInFormat(
                0
            )
        )}
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        parentView = binding.asteroidsListContainer

        viewModel.getLiveData(getTheDateInFormat(0)).observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appState: com.test.application.core.utils.AppState?) {
        dataRenderer.render(appState)

        when(appState) {
            is com.test.application.core.utils.AppState.SuccessAsteroidList -> {
                binding.asteroidsList.adapter = AsteroidsListAdapter(appState.asteroidList,
                binding.asteroidsList, context)
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}