package com.example.thedog.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thedog.DogsViewModel
import com.example.thedog.MainActivity
import com.example.thedog.R
import com.example.thedog.databinding.FragmentDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DetailsFragment"

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DogsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding
    private val image: ImageView by lazy { binding.root.findViewById(R.id.image) }
    private val nameButton: Button by lazy { binding.root.findViewById(R.id.nameButton) }
    private val bredFor: TextView by lazy { binding.root.findViewById(R.id.bredFor) }
    private val breedGroup: TextView by lazy { binding.root.findViewById(R.id.breedGroup) }
    private val height: TextView by lazy { binding.root.findViewById(R.id.height) }
    private val weight: TextView by lazy { binding.root.findViewById(R.id.weight) }
    private val lifeSpan: TextView by lazy { binding.root.findViewById(R.id.lifeSpan) }
    private val temperament: TextView by lazy { binding.root.findViewById(R.id.temperament) }
    private val likeButton: FloatingActionButton by lazy { binding.root.findViewById(R.id.likeButton) }
    private val dislikeButton: FloatingActionButton by lazy { binding.root.findViewById(R.id.dislikeButton) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DetailsFragment.")
        binding = FragmentDetailsBinding.bind(view)

        val dog = args.dog

        dog.also {
            Glide.with(this).load(it.imageUrl).into(image)
            nameButton.text = it.name
            bredFor.text = it.bredFor
            breedGroup.text = it.breedGroup
            height.text = it.height
            weight.text = it.weight
            lifeSpan.text = it.lifeSpan
            temperament.text = it.temperament
            if (!it.isLiked) {
                likeButton.isVisible = true
                dislikeButton.isVisible = false
            } else {
                dislikeButton.isVisible = true
                likeButton.isVisible = false
            }
        }

        binding.nameButton.setOnClickListener {
            Log.d(TAG, "BACK button pressed.")
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

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
        Log.d(TAG, "DetailsFragment is created.")
    }
}