package com.gb_materialdesign.ui.main.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentEarthPictureBinding
import com.gb_materialdesign.model.earthPicture.EarthPictureResponseItem
import com.gb_materialdesign.utils.DSCOVR_EPIC_DOMAIN

class EarthPictureFragment : Fragment() {

    private var _binding: FragmentEarthPictureBinding? = null
    private val binding get() = _binding!!

    private lateinit var earthPicture: EarthPictureResponseItem

    companion object {
        fun newInstance(picture : EarthPictureResponseItem): EarthPictureFragment {
            val fragment = EarthPictureFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("picture", picture)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEarthPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        earthPicture = arguments?.getParcelable("picture")?: EarthPictureResponseItem()

        displayPicture(earthPicture)
    }

    private fun displayPicture(earthPicture: EarthPictureResponseItem) {

        with (binding) {
            pictureTitle.text = earthPicture.caption

            val url = getCorrectUrl(earthPicture)
            earthImage.load(url){
                lifecycle(this@EarthPictureFragment)
                error(R.drawable.ic_load_error_vector)
                placeholder(R.drawable.ic_no_photo_vector)
                crossfade(true)
            }

            pictureDate.text = earthPicture.date

            coordinateLat.text = earthPicture.centroidCoordinates?.lat.toString()
            coordinateLon.text = earthPicture.centroidCoordinates?.lon.toString()
        }
    }

    private fun getCorrectUrl(data: EarthPictureResponseItem): String {

        val date = data.date
        val year = date?.substring(0, 4)
        val month = date?.substring(5, 7)
        val day = date?.substring(8, 10)

        return DSCOVR_EPIC_DOMAIN + year + "/" + month + "/" + day + "/png/" + data.image + ".png"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}