package com.example.thedog

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.thedog.databinding.FragmentDetailsBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "DetailsFragment"

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var viewModel: DogsViewModel
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DetailsFragment.")
        binding = FragmentDetailsBinding.bind(view)

        viewModel = (activity as MainActivity).viewModel
        val dog = args.dog

        binding.webView.apply {
            webViewClient = WebViewClient()
            dog.imageUrl?.let {
                loadUrl(it)
            }
        }
        binding.like.setOnClickListener {
            viewModel.addToLikedDogs(dog)
            Snackbar.make(view, "Added to liked dogs.", Snackbar.LENGTH_SHORT).show()
        }
        Log.d(TAG, "DetailsFragment is created.")
    }
}