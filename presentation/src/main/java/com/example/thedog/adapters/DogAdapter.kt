package com.example.thedog.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Dog
import com.example.thedog.R

private const val TAG = "DogAdapter"

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {
    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private lateinit var dogImage: ImageView
    private lateinit var dogName: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        Log.d(TAG, "Creating DogViewHolder.")
        return DogViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        Log.d(TAG, "Binding DogViewHolder.")
        val dog = differ.currentList[position]

        dogImage = holder.itemView.findViewById(R.id.image)
        dogName = holder.itemView.findViewById(R.id.name)

        holder.itemView.apply {
            Glide.with(this).load(dog.imageUrl).into(dogImage)
            dogName.text = dog.name

            setOnClickListener {
                onItemClickListener?.let {
                    it(dog)
                }
            }
        }
        Log.d(TAG, "DogViewHolder is bound.")
    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnItemClickListener(listener: (Dog) -> Unit) {
        Log.d(TAG, "Setting onItemClickListener.")
        onItemClickListener = listener
    }
}