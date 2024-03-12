package com.example.thedog.fragments

import android.os.Bundle
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
import com.example.thedog.util.viewBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel by viewModels<DogsViewModel>()
    private val args: DetailsFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentDetailsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image: ImageView = binding.root.findViewById(R.id.image)
        val nameButton: Button = binding.root.findViewById(R.id.nameButton)
        val bredFor: TextView = binding.root.findViewById(R.id.bredFor)
        val breedGroup: TextView = binding.root.findViewById(R.id.breedGroup)
        val height: TextView = binding.root.findViewById(R.id.height)
        val weight: TextView = binding.root.findViewById(R.id.weight)
        val lifeSpan: TextView = binding.root.findViewById(R.id.lifeSpan)
        val temperament: TextView = binding.root.findViewById(R.id.temperament)
        val likeButton: FloatingActionButton = binding.root.findViewById(R.id.likeButton)
        val dislikeButton: FloatingActionButton = binding.root.findViewById(R.id.dislikeButton)
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
            likeButton.isVisible = !it.isLiked
            dislikeButton.isVisible = it.isLiked
        }

        binding.nameButton.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

        likeButton.setOnClickListener {
            viewModel.addDog(dog)
            dog.isLiked = true
            it.isVisible = false
            dislikeButton.isVisible = true
            Snackbar.make(it, "Added to liked dogs.", 500).show()
        }

        dislikeButton.setOnClickListener {
            viewModel.deleteDog(dog)
            dog.isLiked = false
            it.isVisible = false
            likeButton.isVisible = true
            Snackbar.make(it, "Deleted from liked dogs.", 500).show()
        }
    }
}