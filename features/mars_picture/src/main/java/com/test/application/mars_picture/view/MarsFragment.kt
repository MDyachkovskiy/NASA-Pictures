package com.test.application.mars_picture.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.test.application.core.domain.marsPicture.MarsCamera
import com.test.application.mars_picture.R
import com.test.application.mars_picture.databinding.FragmentMarsBinding

class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAppBar()
        setDrawerMenuItem()
    }

    private fun setDrawerMenuItem() {
        val navView = binding.marsNavigationView

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navcam -> {
                    replaceFragment(
                        MarsCamera(name = "navcam")
                    )
                    true
                }

                R.id.chemcam -> {
                    replaceFragment(
                        MarsCamera(name = "chemcam")
                    )
                    true
                }

                R.id.fhaz -> {
                    replaceFragment(
                        MarsCamera(name = "fhaz")
                    )
                    true
                }

                else -> false
            }
        }
    }

    private fun setAppBar() {
        val toolbar = binding.marsAppBar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            val drawerLayout = binding.marsDrawerLayout
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun replaceFragment(camera: MarsCamera) {
        val drawerLayout = binding.marsDrawerLayout
        childFragmentManager.beginTransaction()
            .replace(R.id.mars_container, MarsViewPagerFragment.newInstance(camera))
            .addToBackStack("tag")
            .commit()
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}