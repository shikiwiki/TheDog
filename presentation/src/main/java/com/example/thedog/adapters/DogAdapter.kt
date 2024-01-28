package com.example.thedog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.DogToDelete
import com.example.thedog.viewModel.TheDogViewModel

class DogAdapter(private val dogs: List<DogToDelete>, private val viewModel: TheDogViewModel) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(com.example.thedog.R.layout.fragment_item, parent, false)
        return DogViewHolder(itemView)
    }

    override fun getItemCount(): Int = dogs.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val currentDog = dogs[position]

//        holder.image.setImageResource(currentDog.imageUrl)
//        holder.itemView. .text = currentDog.name

    }

    class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val name: TextView = itemView.findViewById(com.example.thedog.R.id.name)
//        val image: ImageView = itemView.findViewById(com.example.thedog.R.id.image)

    }
}