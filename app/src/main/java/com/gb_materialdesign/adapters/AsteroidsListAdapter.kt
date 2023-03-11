package com.gb_materialdesign.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Transition
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.gb_materialdesign.databinding.ItemAsteroidsListBinding
import com.gb_materialdesign.model.asteroids.Asteroid
import com.gb_materialdesign.model.asteroids.AsteroidsListResponse

class AsteroidsListAdapter(
    private val asteroidList: AsteroidsListResponse,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<AsteroidsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAsteroidsListBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroids = asteroidList.nearEarthObjects.asteroids
        if (asteroids != null) {
            holder.bind(asteroids[position])
            holder.itemView.setOnClickListener {
                explodeAnimation(it)
            }
        }
    }

    private fun explodeAnimation(it: View) {
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