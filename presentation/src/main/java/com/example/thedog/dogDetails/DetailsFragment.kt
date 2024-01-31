package com.example.thedog.dogDetails

//import com.example.thedog.ARG_PARAM1
//import com.example.thedog.ARG_PARAM2
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.thedog.MainActivity
import com.example.thedog.R
import com.example.thedog.databinding.FragmentDetailsBinding
import com.example.thedog.dogs.DogsViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var dogsViewModel: DogsViewModel
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
}