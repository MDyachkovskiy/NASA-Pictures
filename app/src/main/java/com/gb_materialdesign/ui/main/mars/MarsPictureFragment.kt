package com.gb_materialdesign.ui.main.mars

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.FragmentMarsPictureBinding
import com.test.application.remote_data.dto.marsPictureResponse.Photo

class MarsPictureFragment : Fragment() {

    private var _binding: FragmentMarsPictureBinding? = null
    private val binding get() = _binding!!

    private var isImageScaled = false

    private lateinit var marsPicture: com.test.application.remote_data.dto.marsPictureResponse.Photo

    companion object {
        fun newInstance (marsPicture: com.test.application.remote_data.dto.marsPictureResponse.Photo) : MarsPictureFragment {
            val fragment = MarsPictureFragment()
            fragment.arguments = Bundle().apply{
                putParcelable("marsPicture", marsPicture)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarsPictureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        marsPicture = arguments?.getParcelable("marsPicture")?: com.test.application.remote_data.dto.marsPictureResponse.Photo()

        binding.marsPicture.setOnClickListener {
            pictureAnimation(it)
        }

        displayPicture(marsPicture)
    }

    private fun pictureAnimation(it: View) {
        isImageScaled = !isImageScaled

        val params = it.layoutParams as FrameLayout.LayoutParams

        val transitionSet = TransitionSet()
        val changeImageTransform = ChangeImageTransform().apply {
            duration = 2000
        }
        val changeBounds = ChangeBounds()

        transitionSet
            .addTransition(changeBounds)
            .addTransition(changeImageTransform)

        TransitionManager.beginDelayedTransition(binding.root, transitionSet)
        if (isImageScaled) {
            params.height = ConstraintLayout.LayoutParams.MATCH_PARENT
            (it as AppCompatImageView).scaleType = ImageView.ScaleType.CENTER_CROP
        } else {
            params.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
            (it as AppCompatImageView).scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
        it.layoutParams = params
    }

    private fun displayPicture (marsPicture: com.test.application.remote_data.dto.marsPictureResponse.Photo) {

        with(binding) {

            roverName.text = marsPicture.rover.name
            roverCamera.text = marsPicture.camera.fullName
            earthDate.text = marsPicture.earthDate
            marsDate.text = marsPicture.sol.toString()

            val options = RequestOptions()
                .error(R.drawable.ic_load_error_vector)
                .placeholder(R.drawable.ic_no_photo_vector)

            activity?.let{
                Glide.with(it)
                    .load(marsPicture.imgSrc)
                    .apply(options)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(binding.marsPicture)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}