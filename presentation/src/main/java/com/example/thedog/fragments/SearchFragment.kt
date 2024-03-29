package com.example.thedog.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.util.Constants
import com.example.data.util.Constants.Companion.SEARCH_INPUT_DELAY
import com.example.data.util.Status
import com.example.thedog.DogsViewModel
import com.example.thedog.R
import com.example.thedog.adapters.DogAdapter
import com.example.thedog.databinding.FragmentSearchBinding
import com.example.thedog.util.Constants.Companion.DOG_KEY
import com.example.thedog.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val viewModel by viewModels<DogsViewModel>()
    private val dogAdapter: DogAdapter by lazy {
        DogAdapter(
            { dog -> viewModel.addDog(dog) },
            { dog -> viewModel.deleteDog(dog) }
        )
    }
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private var inputText = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchDogsRecyclerView()

        var job: Job? = null
        binding.searchField.addTextChangedListener { inputBreed ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(SEARCH_INPUT_DELAY)
                val breed = inputBreed.toString()
                inputBreed?.let {
                    if (breed.isNotEmpty() && breed != inputText) {
                        inputText = breed
                        viewModel.searchDogs(breed)
                    }
                }
            }
        }

        dogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(DOG_KEY, it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.sharedSearchDogsLivaData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showProgressBar()
                }

                Status.SUCCESS -> {
                    hideProgressBar()
                    resource.data?.let { dogs ->
                        dogAdapter.differ.submitList(dogs.toList())
                        val totalPages = dogs.size / Constants.LIMIT_PER_PAGE + 2
                        isLastPage = viewModel.searchDogsPage == totalPages
                        if (isLastPage) {
                            binding.recyclerSearchDogs.setPadding(0, 0, 0, 0)
                        }
                    }
                }

                Status.ERROR -> {
                    hideProgressBar()
                    resource.message?.let { message ->
                        Toast.makeText(activity, "Sorry, $message", Toast.LENGTH_SHORT).show()
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
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        isLoading = true
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

    private fun setupSearchDogsRecyclerView() {
        dogAdapter.isInSearchFragment()
        binding.recyclerSearchDogs.apply {
            adapter = dogAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchFragment.scrollListener)
        }
    }
}