package com.example.thedog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.data.util.Status
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentDogsBinding

private const val TAG = "DogsFragment"

class DogsFragment : Fragment(R.layout.fragment_dogs) {
    val viewModel: DogsViewModel by lazy { (activity as MainActivity).viewModel }
    private val dogAdapter: DogAdapter by lazy { DogAdapter(viewModel) }
    private var retryButton: Button? = null
    private var errorText: TextView? = null
    private val itemDogsError: CardView by lazy { binding.root.findViewById(R.id.dogItemError) }
    private lateinit var binding: FragmentDogsBinding


    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DogsFragment.")

        binding = FragmentDogsBinding.bind(view)

//        itemDogsError = view.findViewById(R.id.dogItemError)

        setupDogsRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateDogs()
            observeViewModel()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        val inflater =
            requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val errorView: View = inflater.inflate(R.layout.fragment_error, null)

        retryButton = errorView.findViewById(R.id.retryButton)
        errorText = errorView.findViewById(R.id.errorText)

        dogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("dog", it)
            }
            findNavController().navigate(R.id.action_dogsFragment_to_detailsFragment, bundle)
        }

        observeViewModel()
        retryButton!!.setOnClickListener {
            viewModel.getDogs()
        }
        Log.d(TAG, "DogsFragment is created.")
    }

    private fun observeViewModel() {
        viewModel.dogsLivaData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    hideErrorMessage()
                    resource.data?.let { dogs ->
                        dogAdapter.differ.submitList(dogs.toList())
                        val totalPages = dogs.size / LIMIT_PER_PAGE + 2
                        isLastPage = viewModel.page == totalPages
                        if (isLastPage) {
                            binding.recyclerDogs.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                Status.LOADING -> {
                    showProgressBar()
                }

                Status.ERROR -> {
                    hideProgressBar()
                    resource.message?.let { message ->
                        Toast.makeText(activity, "Sorry, $message", Toast.LENGTH_LONG)
                            .show()
                        showErrorMessage(message)
                    }
                }
            }
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
        isLoading = false
        Log.d(TAG, "ProgressBar is hidden.")
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
        Log.d(TAG, "ProgressBar is shown.")
    }

    private fun hideErrorMessage() {
        itemDogsError.visibility = View.INVISIBLE
        isError = false
        Log.d(TAG, "Error message is hidden.")
    }

    private fun showErrorMessage(message: String) {
        itemDogsError.visibility = View.VISIBLE
        errorText!!.text = message
        isError = true
        Log.d(TAG, "Error message is shown.")
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
                viewModel.getDogs()
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

    private fun setupDogsRecyclerView() {
        Log.d(TAG, "Setting up DogRecycler.")
        binding.recyclerDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@DogsFragment.scrollListener)
        }
        Log.d(TAG, "DogRecycler is set up.")
    }

}