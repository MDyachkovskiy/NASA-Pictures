package com.gb_materialdesign.ui.main.mars

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentMarsBinding
import com.test.application.remote_data.dto.marsPictureResponse.Camera

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
            when(menuItem.itemId) {
                R.id.navcam -> {
                    replaceFragment(
                        com.test.application.remote_data.dto.marsPictureResponse.Camera(
                            name = "navcam"
                        )
                    )
                    true
                }
                R.id.chemcam -> {
                    replaceFragment(
                        com.test.application.remote_data.dto.marsPictureResponse.Camera(
                            name = "chemcam"
                        )
                    )
                    true
                }
                R.id.fhaz -> {
                    replaceFragment(
                        com.test.application.remote_data.dto.marsPictureResponse.Camera(
                            name = "fhaz"
                        )
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
            val drawerLayout = binding.marsFragment
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun replaceFragment(camera: com.test.application.remote_data.dto.marsPictureResponse.Camera) {

        val drawerLayout = binding.marsFragment
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