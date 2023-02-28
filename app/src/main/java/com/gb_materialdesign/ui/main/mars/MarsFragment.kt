package com.gb_materialdesign.ui.main.mars

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentMarsBinding
import com.gb_materialdesign.model.marsPicture.Camera
import com.gb_materialdesign.ui.main.navigationDrawer.BottomNavigationDrawerFragment

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
    }

    private fun setAppBar() {
        val context = activity as MainActivity
        context.setSupportActionBar(view?.findViewById(R.id.marsAppBar))
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }

            R.id.navcam -> {
                val camera = Camera(name= "navcam")
                childFragmentManager.beginTransaction()
                    .replace(R.id.marsContainer, MarsViewPagerFragment.newInstance(camera))
                    .addToBackStack("tag")
                    .commit()
            }
            R.id.chemcam -> {
                val camera = Camera(name= "chemcam")
                childFragmentManager.beginTransaction()
                    .replace(R.id.marsContainer, MarsViewPagerFragment.newInstance(camera))
                    .addToBackStack("tag")
                    .commit()
            }
            R.id.fhaz -> {
                val camera = Camera(name= "fhaz")
                childFragmentManager.beginTransaction()
                    .replace(R.id.marsContainer, MarsViewPagerFragment.newInstance(camera))
                    .addToBackStack("tag")
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}