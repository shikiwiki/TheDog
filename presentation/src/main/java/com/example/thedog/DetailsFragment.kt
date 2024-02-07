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

    lateinit var dogsViewModel: DogsViewModel
    val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "Creating DetailsFragment.")

        binding = FragmentDetailsBinding.bind(view)

        dogsViewModel = (activity as MainActivity).dogViewModel
        val dogResponseItem = args.dogResponseItem

        binding.webView.apply {
            webViewClient = WebViewClient()
            dogResponseItem.url?.let {
                loadUrl(it)
            }
        }
        binding.like.setOnClickListener {
            dogsViewModel.addToLikedDogs(dogResponseItem)
            Snackbar.make(view, "Added to liked dogs.", Snackbar.LENGTH_SHORT).show()
        }
        Log.d(TAG, "DetailsFragment is created.")
    }
}