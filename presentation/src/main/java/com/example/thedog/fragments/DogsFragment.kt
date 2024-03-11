package com.example.thedog.fragments

import android.animation.Animator
import android.animation.Animator.AnimatorListener
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
import com.example.thedog.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "DogsFragment"

@AndroidEntryPoint
class DogsFragment : Fragment(R.layout.fragment_dogs) {

    private val viewModel by viewModels<DogsViewModel>()
    private val dogAdapter: DogAdapter by lazy {
        DogAdapter(
            { dog -> viewModel.addDog(dog) },
            { dog -> viewModel.deleteDog(dog) }
        )
    }
    private val binding by viewBinding(FragmentDogsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "Creating DogsFragment.")
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.sharedAllDogsLivaData.observe(viewLifecycleOwner) { resource ->
            Log.d(TAG, "Observing ViewModel's dogsLivaData")
            when (resource.status) {
                Status.LOADING -> {
                    Log.d(TAG, "Status LOADING from ViewModel's allDogsLivaData")
                    showAnimation()
                }

                Status.SUCCESS -> {
                    Log.d(TAG, "Status SUCCESS from ViewModel's allDogsLivaData")
                    hideAnimation()
                    resource.data?.let { dogs ->
                        dogAdapter.differ.submitList(dogs.toList())
                        val totalPages = dogs.size / Constants.LIMIT_PER_PAGE + 2
                        isLastPage = viewModel.allDogsPage == totalPages
                        if (isLastPage) {
                            binding.recyclerDogs.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                Status.ERROR -> {
                    Log.d(TAG, "Status ERROR from ViewModel's allDogsLivaData")
                    hideAnimation()
                    resource.message?.let { message ->
                        Toast.makeText(activity, "Sorry, $message", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun hideAnimation() {
        Log.d(TAG, "Hiding animation.")

        binding.apply {
            lottieView.visibility - View.INVISIBLE
            lottieView.pauseAnimation()
        }
        Log.d(TAG, "Animation is hidden.")
    }

    private fun showAnimation() {
        Log.d(TAG, "Showing animation.")
        binding.apply {
            lottieView.setMinAndMaxProgress(0f, 1f)
            lottieView.repeatCount = LottieDrawable.INFINITE
            lottieView.addAnimatorListener(object : AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    lottieView.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {
                    lottieView.visibility = View.INVISIBLE
                }

                override fun onAnimationCancel(animation: Animator) {
                    lottieView.visibility = View.INVISIBLE
                }

                override fun onAnimationRepeat(animation: Animator) {}
            })
            lottieView.playAnimation()
        }
        Log.d(TAG, "Animation is shown.")
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

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