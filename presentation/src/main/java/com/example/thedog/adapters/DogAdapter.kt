package com.example.thedog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.model.DogResponseItem
import com.example.domain.model.DogToDelete
import com.example.thedog.R
import com.example.thedog.viewModel.TheDogViewModel

class DogAdapter(private val dogs: List<DogToDelete>, private val viewModel: TheDogViewModel) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    lateinit var dogImage: ImageView
    lateinit var dogName: TextView

    private val differCallback = object : DiffUtil.ItemCallback<DogResponseItem>() {
        override fun areItemsTheSame(
            oldItem: DogResponseItem,
            newItem: DogResponseItem
        ): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(
            oldItem: DogResponseItem,
            newItem: DogResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((DogResponseItem) -> Unit)? = null

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val dog = differ.currentList[position]

        dogImage = holder.itemView.findViewById(R.id.image)
        dogName = holder.itemView.findViewById(R.id.name)

        holder.itemView.apply {
            Glide.with(this).load(dog.url).into(dogImage)
            dogName.text = dog.breeds[0].name

            setOnClickListener {
                onItemClickListener?.let {
                    it(dog)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (DogResponseItem) -> Unit) {
        onItemClickListener = listener
    }

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}