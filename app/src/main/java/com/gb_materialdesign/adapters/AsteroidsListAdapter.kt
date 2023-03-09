package com.gb_materialdesign.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gb_materialdesign.databinding.ItemAsteroidsListBinding
import com.gb_materialdesign.model.asteroids.Asteroid
import com.gb_materialdesign.model.asteroids.AsteroidsListResponse

class AsteroidsListAdapter(
    private val asteroidList: AsteroidsListResponse,
): RecyclerView.Adapter<AsteroidsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAsteroidsListBinding.inflate(LayoutInflater.from(parent.context),
        parent, false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroids = asteroidList.nearEarthObjects.nearEarthObjects["2023-03-07"]
        if (asteroids != null) {
            holder.bind(asteroids[position])
        }
    }

    override fun getItemCount(): Int {
        return asteroidList.elementCount
    }

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind (asteroid: Asteroid){
            val binding = ItemAsteroidsListBinding.bind(itemView)

            with(binding){
                asteroidName.text = asteroid.name
                asteroidMissDistance.text = asteroid.closeApproachData[1].missDistance.kilometers
            }
        }
    }
}