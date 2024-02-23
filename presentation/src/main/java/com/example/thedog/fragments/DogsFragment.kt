package com.example.thedog.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.example.data.util.Constants
import com.example.data.util.Status
import com.example.thedog.DogsViewModel
import com.example.thedog.R
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentDogsBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DogsFragment"

@AndroidEntryPoint
class DogsFragment : Fragment(R.layout.fragment_dogs) {

    private val viewModel by viewModels<DogsViewModel>()
    private val dogAdapter: DogAdapter by lazy { DogAdapter(viewModel) }
    private lateinit var binding: FragmentDogsBinding

    @SuppressLint("InflateParams")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DogsFragment.")

        binding = FragmentDogsBinding.bind(view)

        setupDogsRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateDogs()
            observeViewModel()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        dogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("dog", it)
            }
            findNavController().navigate(R.id.action_dogsFragment_to_detailsFragment, bundle)
        }

        observeViewModel()
        Log.d(TAG, "DogsFragment is created.")
    }

    private fun observeViewModel() {
        viewModel.dogsLivaData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    hideProgressBar()
                    binding.apply {
                        lottieView.pauseAnimation()
                        lottieView.visibility - View.INVISIBLE
                    }
//                    binding.lottieView.stop
                    resource.data?.let { dogs ->
                        dogAdapter.differ.submitList(dogs.toList())
                        val totalPages = dogs.size / Constants.LIMIT_PER_PAGE + 2
                        isLastPage = viewModel.page == totalPages
                        if (isLastPage) {
                            binding.recyclerDogs.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                Status.LOADING -> {
                    showProgressBar()
                    binding.apply {
                        lottieView.visibility = View.VISIBLE
                        lottieView.setMinProgress(0f)
                        lottieView.setMaxProgress(1f)
                        lottieView.playAnimation()
                        lottieView.repeatCount = LottieDrawable.INFINITE
//                    binding.lottieView.repeatMode = LottieDrawable.RESTART
                    }
                }

                Status.ERROR -> {
                    binding.apply {
                        lottieView.pauseAnimation()
                        lottieView.visibility - View.INVISIBLE
                    }
                    hideProgressBar()
                    resource.message?.let { message ->
                        Toast.makeText(activity, "Sorry, $message", Toast.LENGTH_SHORT)
                            .show()
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

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThenVisible = totalItemCount >= Constants.LIMIT_PER_PAGE
            val shouldPaginate =
                !isError && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThenVisible && isScrolling
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
        dogAdapter.isInDogsFragment()
        binding.recyclerDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@DogsFragment.scrollListener)
        }
        Log.d(TAG, "DogRecycler is set up.")
    }
}