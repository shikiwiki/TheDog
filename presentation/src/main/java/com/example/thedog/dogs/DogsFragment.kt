package com.example.thedog.dogs

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.util.Constants.Companion.LIMIT_PER_PAGE
import com.example.data.util.Resource
import com.example.thedog.MainActivity
import com.example.thedog.R
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentDogsBinding

class DogsFragment : Fragment(R.layout.fragment_dogs) {
    lateinit var dogsViewModel: DogsViewModel
    private lateinit var dogAdapter: DogAdapter
    private lateinit var retryButton: Button //or delete
    private lateinit var errorText: TextView //or delete
    private lateinit var itemDogsError: CardView
    private lateinit var binding: FragmentDogsBinding

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDogsBinding.bind(view)

        itemDogsError = view.findViewById(R.id.dogItemError)

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val errorView: View = inflater.inflate(R.layout.fragment_error, null)

        retryButton = errorView.findViewById(R.id.retryButton)
        errorText = errorView.findViewById(R.id.errorText)

        dogsViewModel = (activity as MainActivity).dogViewModel
        setupDogRecycler()

        dogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("dog", it)
            }
            findNavController().navigate(R.id.action_dogsFragment_to_detailsFragment, bundle)
        }

        dogsViewModel.dogs.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success<*> -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { dogResponse ->
                        dogAdapter.differ.submitList(dogResponse.toList())
                        val totalPages = dogResponse.size / LIMIT_PER_PAGE + 2
                        isLastPage = dogsViewModel.page == totalPages
                        if (isLastPage) {
                            binding.recyclerDogs.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                is Resource.Loading<*> -> {
                    showProgressBar()
                }

                is Resource.Error<*> -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Sorry, error: $message", Toast.LENGTH_LONG).show()
                        showErrorMessage(message)
                    }
                }
            }
        }
        retryButton.setOnClickListener {
            dogsViewModel.getDogs()
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideErrorMessage() {
        itemDogsError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showErrorMessage(message: String) {
        itemDogsError.visibility = View.VISIBLE
        errorText.text = message
        isError = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThenVisible = totalItemCount >= LIMIT_PER_PAGE
            val shouldPaginate =
                isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThenVisible && isScrolling
            if (shouldPaginate) {
                dogsViewModel.getDogs()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupDogRecycler() {
        dogAdapter = DogAdapter()
        binding.recyclerDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@DogsFragment.scrollListener)
        }
    }
}