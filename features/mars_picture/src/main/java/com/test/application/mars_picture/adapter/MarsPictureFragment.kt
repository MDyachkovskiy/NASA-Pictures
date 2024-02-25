package com.test.application.mars_picture.adapter

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
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
import com.test.application.core.domain.marsPicture.MarsPhoto
import com.test.application.mars_picture.databinding.FragmentMarsPictureBinding
import com.test.application.mars_picture.utils.MARS_BUNDLE_KEY

class MarsPictureFragment : Fragment() {

    private var _binding: FragmentMarsPictureBinding? = null
    private val binding get() = _binding!!

    private var isImageScaled = false

    private val marsPicture: MarsPhoto by lazy {
        requireArguments().getParcelable(MARS_BUNDLE_KEY) ?: MarsPhoto()
    }

    companion object {
        fun newInstance(marsPicture: MarsPhoto) = MarsPictureFragment().apply {
            arguments = Bundle().apply {
                putParcelable(MARS_BUNDLE_KEY, marsPicture)
            }
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
        renderData(marsPicture)
        binding.marsPicture.setOnClickListener { pictureAnimation(it) }
    }

    private fun pictureAnimation(view: View) {
        isImageScaled = !isImageScaled

        val params = view.layoutParams as FrameLayout.LayoutParams
        TransitionManager.beginDelayedTransition(
            binding.root,
            TransitionSet().apply {
                addTransition(ChangeBounds())
                addTransition(ChangeImageTransform().apply {
                    duration = 2000
                })
            }
        )

        params.height = if(isImageScaled) {
            ConstraintLayout.LayoutParams.MATCH_PARENT
        } else {
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        }

        val scaleType = if(isImageScaled) {
            ImageView.ScaleType.CENTER_CROP
        } else {
            ImageView.ScaleType.CENTER_INSIDE
        }

        (view as? ImageView)?.apply {
            layoutParams = params
            this.scaleType = scaleType
        }
    }

    private fun renderData(marsPicture: MarsPhoto) {
        displayImageData(marsPicture)
        displayTextData(marsPicture)
    }

    private fun displayTextData(marsPicture: MarsPhoto) {
        with(binding) {
            roverName.text = marsPicture.rover.name
            roverCamera.text = marsPicture.camera.fullName
            earthDate.text = marsPicture.earthDate
            marsDate.text = marsPicture.sol.toString()
        }
    }

    private fun displayImageData(marsPictures: MarsPhoto) {
        with(binding) {
            Glide.with(requireContext())
                .load(marsPictures.imgSrc)
                .placeholder(com.test.application.core.R.drawable.ic_no_photo_vector)
                .error(com.test.application.core.R.drawable.ic_load_error_vector)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                })
                .into(marsPicture)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}