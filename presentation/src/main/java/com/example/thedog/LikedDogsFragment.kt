package com.example.thedog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentLikedDogsBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "LikedDogsFragment"

class LikedDogsFragment : Fragment(R.layout.fragment_liked_dogs) {

    lateinit var viewModel: DogsViewModel
    lateinit var dogAdapter: DogAdapter
    private lateinit var binding: FragmentLikedDogsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "Creating LikedDogsFragment.")

        binding = FragmentLikedDogsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        setupLikedDogsRecyclerView()

        dogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("dog", it)
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
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val dog = dogAdapter.differ.currentList[position]
                viewModel.deleteDog(dog)
                Snackbar.make(view, "Removed from liked dogs.", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.addToLikedDogs(dog)
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
        Log.d(TAG, "LikedDogsFragment is created.")
    }

    private fun setupLikedDogsRecyclerView() {
        Log.d(TAG, "Setting up LikedDogsRecycler.")
        dogAdapter = DogAdapter()
        binding.recyclerLikedDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        Log.d(TAG, "LikedDogsRecycler is set up.")
    }
}