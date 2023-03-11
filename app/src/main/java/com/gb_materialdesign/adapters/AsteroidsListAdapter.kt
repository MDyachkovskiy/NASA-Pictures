package com.gb_materialdesign.adapters

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.gb_materialdesign.MainActivity
import com.gb_materialdesign.R
import com.gb_materialdesign.databinding.ItemAsteroidsListBinding
import com.gb_materialdesign.model.asteroids.Asteroid
import com.gb_materialdesign.model.asteroids.AsteroidsListResponse
import com.gb_materialdesign.ui.main.asteroids.AsteroidDetailsFragment
import com.gb_materialdesign.utils.KEY_BUNDLE_ASTEROID

class AsteroidsListAdapter(
    private val asteroidList: AsteroidsListResponse,
    private val recyclerView: RecyclerView,
    private val context: Context?
): RecyclerView.Adapter<AsteroidsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAsteroidsListBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroids = asteroidList.nearEarthObjects.asteroids
        if (asteroids != null) {
            val asteroid = asteroids[position]
            holder.bind(asteroids[position])
            holder.itemView.setOnClickListener {
                explodeAnimation(it, asteroid)
            }
        }
    }

    private fun openDetailsFragment(asteroid: Asteroid) {
        val fragment = AsteroidDetailsFragment.newInstance(Bundle())
        fragment.arguments = Bundle().apply {
            putParcelable(KEY_BUNDLE_ASTEROID, asteroid)
        }

        Thread.sleep(2000)

        (context as MainActivity).supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.space_container, fragment)
            .commit()

    }

    private fun explodeAnimation(it: View, asteroid: Asteroid) {
        val myAutoTransition = TransitionSet()
        myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
        val explode = Explode()
        explode.duration = 1000
        val rect = Rect()
        it.getGlobalVisibleRect(rect)
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return rect
            }
        }
        myAutoTransition.addTransition(explode)
        TransitionManager.beginDelayedTransition(recyclerView, myAutoTransition)
        recyclerView.adapter = null
        openDetailsFragment(asteroid)
    }

    override fun getItemCount(): Int {
        return asteroidList.elementCount
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind (asteroid: Asteroid){
            val binding = ItemAsteroidsListBinding.bind(itemView)

            with(binding){
                asteroidName.text = asteroid.name
                asteroidMissDistance.text = asteroid.closeApproachData[0].missDistance.kilometers
            }
        }
    }
}