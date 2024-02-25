package com.test.application.asteroids.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.test.application.asteroids.databinding.ItemAsteroidsListBinding
import com.test.application.core.domain.asteroids.Asteroid
import com.test.application.core.domain.asteroids.AsteroidsList

class AsteroidsListAdapter(
    private val asteroidList: AsteroidsList,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AsteroidsListAdapter.ViewHolder>() {

    var onAsteroidClick: ((Asteroid) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAsteroidsListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroids = asteroidList.nearEarthObjects.asteroids
        val asteroid = asteroids[position]
        holder.bind(asteroids[position])
        holder.itemView.setOnClickListener {
            explodeAnimation(it, asteroid)
        }
    }

    private fun explodeAnimation(view: View, asteroid: Asteroid) {
        val myAutoTransition = TransitionSet()
        myAutoTransition.ordering = TransitionSet.ORDERING_SEQUENTIAL
        val explode = Explode()
        explode.duration = 1000
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        explode.epicenterCallback = object : Transition.EpicenterCallback() {
            override fun onGetEpicenter(transition: Transition): Rect {
                return rect
            }
        }
        myAutoTransition.addTransition(explode)
        TransitionManager.beginDelayedTransition(recyclerView, myAutoTransition)
        recyclerView.adapter = null

        view.postDelayed({ onAsteroidClick?.invoke(asteroid) }, 1000)
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