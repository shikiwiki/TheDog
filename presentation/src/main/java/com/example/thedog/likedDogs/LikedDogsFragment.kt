package com.example.thedog.likedDogs

//import com.example.thedog.ARG_PARAM1
//import com.example.thedog.ARG_PARAM2
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thedog.MainActivity
import com.example.thedog.R
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentLikedDogsBinding
import com.example.thedog.dogs.DogsViewModel
import com.google.android.material.snackbar.Snackbar

class LikedDogsFragment : Fragment(R.layout.fragment_liked_dogs) {

    lateinit var dogsViewModel: DogsViewModel
    lateinit var dogAdapter: DogAdapter
    private lateinit var binding: FragmentLikedDogsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLikedDogsBinding.bind(view)

        dogsViewModel = (activity as MainActivity).dogViewModel
        setupLikedDogsRecycler()

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
                //TODO TRY DIFFERENT GETTERS BUT NOT LAYOUT ONE
                val position = viewHolder.bindingAdapterPosition
                val dog = dogAdapter.differ.currentList[position]
                dogsViewModel.deleteDog(dog)
                Snackbar.make(view, "Removed from liked dogs.", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        dogsViewModel.addToLikedDogs(dog)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.recyclerLikedDogs)
        }
        dogsViewModel.getLikedDogs().observe(viewLifecycleOwner) { dogs ->
            dogAdapter.differ.submitList(dogs)
        }
    }

    private fun setupLikedDogsRecycler() {
        dogAdapter = DogAdapter()
        binding.recyclerLikedDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}