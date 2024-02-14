package com.example.thedog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.thedog.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "DetailsFragment"

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var viewModel: DogsViewModel
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DetailsFragment.")
        binding = FragmentDetailsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
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
        }

        binding.nameButton.setOnClickListener {
            Log.d(TAG, "BACK button pressed.")
            (activity as MainActivity).supportFragmentManager.popBackStack()
        }

        binding.like.setOnClickListener {
            viewModel.addToLikedDogs(dog)
            Snackbar.make(view, "Added to liked dogs.", Snackbar.LENGTH_SHORT).show()
        }
        Log.d(TAG, "DetailsFragment is created.")
    }
}