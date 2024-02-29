package com.example.thedog.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.Dog
import com.example.thedog.DogsViewModel
import com.example.thedog.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

private const val TAG = "DogAdapter"

class DogAdapter(
    private val viewModel: DogsViewModel
) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {
    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

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

        val dogImage: ImageView = holder.itemView.findViewById(R.id.image)
        val dogName: TextView = holder.itemView.findViewById(R.id.name)
        val likeButton: FloatingActionButton = holder.itemView.findViewById(R.id.itemLikeButton)
        val dislikeButton: FloatingActionButton =
            holder.itemView.findViewById(R.id.itemDislikeButton)

        holder.itemView.apply {
            Glide.with(this).load(dog.imageUrl).into(dogImage)
            dogName.text = dog.name
            likeButton.isVisible = !dog.isLiked
            dislikeButton.isVisible = dog.isLiked
            setOnClickListener {
                onItemClickListener?.let { it(dog) }
            }
        }

        if (inDogsOrSearchFragment) {
            likeButton.setOnClickListener {
                viewModel.addDog(dog)
                dog.isLiked = true
                likeButton.isVisible = false
                dislikeButton.isVisible = true
                Snackbar.make(it, "Added to liked dogs.", 500).show()
            }

            dislikeButton.setOnClickListener {
                viewModel.deleteDog(dog)
                dog.isLiked = false
                dislikeButton.isVisible = false
                likeButton.isVisible = true
                Snackbar.make(it, "Deleted from liked dogs.", 500).show()
            }
        } else {
            likeButton.isVisible = false
            dislikeButton.isVisible = false
        }
        Log.d(TAG, "DogViewHolder is bound.")
    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnItemClickListener(listener: (Dog) -> Unit) {
        Log.d(TAG, "Setting onItemClickListener.")
        onItemClickListener = listener
    }

    private var inDogsOrSearchFragment = true

    fun isInDogsOrSearchFragment() {
        inDogsOrSearchFragment = true
    }

    fun isInLikedDogsFragment() {
        inDogsOrSearchFragment = false
    }
}