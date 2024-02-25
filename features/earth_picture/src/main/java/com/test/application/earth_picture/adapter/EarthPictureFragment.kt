package com.test.application.earth_picture.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.test.application.core.domain.earthPicture.EarthPictureItem
import com.test.application.core.utils.DSCOVR_EPIC_DOMAIN
import com.test.application.earth_picture.databinding.FragmentEarthPictureBinding
import com.test.application.earth_picture.utils.EARTH_BUNDLE_KEY

class EarthPictureFragment : Fragment() {

    private var _binding: FragmentEarthPictureBinding? = null
    private val binding get() = _binding!!

    private val earthPicture: EarthPictureItem by lazy {
        requireArguments().getParcelable(EARTH_BUNDLE_KEY) ?: EarthPictureItem()
    }

    companion object {
        fun newInstance(picture: EarthPictureItem) = EarthPictureFragment().apply {
           arguments = Bundle().apply { putParcelable(EARTH_BUNDLE_KEY, picture) }
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
        renderData(earthPicture)
    }

    private fun renderData(earthPicture: EarthPictureItem) {
        displayImageData(earthPicture)
        displayTextData(earthPicture)
    }

    private fun displayTextData(earthPicture: EarthPictureItem) {
        with(binding) {
            pictureTitle.text = earthPicture.caption
            pictureDate.text = earthPicture.date
            coordinateLat.text = earthPicture.centroidCoordinates?.lat.toString()
            coordinateLon.text = earthPicture.centroidCoordinates?.lon.toString()
        }
    }

    private fun displayImageData(earthPicture: EarthPictureItem) {
        with(binding) {
            val url = getCorrectUrl(earthPicture)

            earthImage.load(url) {
                crossfade(true)
                placeholder(com.test.application.core.R.drawable.ic_no_photo_vector)
                error(com.test.application.core.R.drawable.ic_load_error_vector)
                listener(
                    onStart = {
                        progressBar.visibility = View.VISIBLE
                    },
                    onSuccess = { _, _ ->
                        progressBar.visibility = View.GONE
                    },
                    onError = { _, _ ->
                        progressBar.visibility = View.GONE
                    }
                )
            }
        }
    }

    private fun getCorrectUrl(data: EarthPictureItem): String {
        val date = data.date
        val year = date?.substring(0, 4)
        val month = date?.substring(5, 7)
        val day = date?.substring(8, 10)
        return DSCOVR_EPIC_DOMAIN + year + "/" + month + "/" + day + "/jpg/" + data.image + ".jpg"
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}