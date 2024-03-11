package com.example.thedog.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thedog.DogsViewModel
import com.example.thedog.R
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentLikedDogsBinding
import com.example.thedog.util.Constants.Companion.DOG_KEY
import com.example.thedog.util.viewBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LikedDogsFragment : Fragment(R.layout.fragment_liked_dogs) {

    private val viewModel by viewModels<DogsViewModel>()
    private val dogAdapter: DogAdapter by lazy {
        DogAdapter(
            { dog -> viewModel.addDog(dog) },
            { dog -> viewModel.deleteDog(dog) }
        )
    }
    private val binding by viewBinding(FragmentLikedDogsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLikedDogsRecyclerView()

        dogAdapter.setOnItemClickListener {
            it.isLiked = true
            val bundle = Bundle().apply {
                putSerializable(DOG_KEY, it)
            }
            findNavController().navigate(R.id.action_likedDogsFragment_to_detailsFragment, bundle)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val dog = dogAdapter.differ.currentList[position]
                viewModel.deleteDog(dog)
                viewModel.getLikedDogs()
                dogAdapter.differ.submitList(viewModel.getLikedDogs().value?.data)
                Snackbar.make(view, "Removed from liked dogs.", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.addDog(dog)
                        viewModel.getLikedDogs()
                        dogAdapter.differ.submitList(viewModel.getLikedDogs().value?.data)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerLikedDogs)
        }
        viewModel.getLikedDogs().observe(viewLifecycleOwner) { dogs ->
            dogAdapter.differ.submitList(dogs.data)
        }
    }

    private fun setupLikedDogsRecyclerView() {
        dogAdapter.isInLikedDogsFragment()
        binding.recyclerLikedDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}