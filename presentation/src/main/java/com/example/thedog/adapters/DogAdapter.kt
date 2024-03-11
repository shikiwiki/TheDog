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
import com.example.thedog.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

private const val TAG = "DogAdapter"

class DogAdapter(
    private val onAddDog: (Dog) -> Unit,
    private val onDeleteDog: (Dog) -> Unit
) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {
    private var inDogsFragment = true
    private var inSearchFragment = false
    private var inLikedDogsFragment = false

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
        return if (inSearchFragment) {
            DogViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.fragment_search_item, parent, false)
            )
        } else {
            DogViewHolder(
                LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.fragment_item, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        Log.d(TAG, "Binding DogViewHolder position $position.")
        val dog = differ.currentList[position]

        val dogImage: ImageView = holder.itemView.findViewById(R.id.image)
        val dogName: TextView = holder.itemView.findViewById(R.id.name)
        val likeButton: FloatingActionButton =
            holder.itemView.findViewById(R.id.itemLikeButton)
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

        if (inDogsFragment || inSearchFragment) {
            likeButton.setOnClickListener {
                onAddDog(dog)
                dog.isLiked = true
                likeButton.isVisible = false
                dislikeButton.isVisible = true
                Snackbar.make(it, "Added to liked dogs.", 500).show()
            }

            dislikeButton.setOnClickListener {
                onDeleteDog(dog)
                dog.isLiked = false
                dislikeButton.isVisible = false
                likeButton.isVisible = true
                Snackbar.make(it, "Deleted from liked dogs.", 500).show()
            }
        } else {
            likeButton.isVisible = false
            dislikeButton.isVisible = false
        }
    }

    private var onItemClickListener: ((Dog) -> Unit)? = null

    fun setOnItemClickListener(listener: (Dog) -> Unit) {
        Log.d(TAG, "Setting onItemClickListener.")
        onItemClickListener = listener
    }

    fun isInDogsFragment() {
        inDogsFragment = true
        inSearchFragment = false
        inLikedDogsFragment = false
        Log.d(
            TAG,
            "We are inDogsFragment"
        )
    }

    fun isInLikedDogsFragment() {
        inDogsFragment = false
        inSearchFragment = false
        inLikedDogsFragment = true
        Log.d(
            TAG,
            "We are inLikedDogsFragment"
        )
    }

    fun isInSearchFragment() {
        inDogsFragment = false
        inSearchFragment = true
        inLikedDogsFragment = false
        Log.d(
            TAG,
            "We are inSearchFragment"
        )
    }
}