package com.example.thedog

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.local.DogResponseItemDatabase
import com.example.data.repository.DogRepositoryImpl
import com.example.thedog.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var dogViewModel: DogsViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Creating MainActivity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dogRepository = DogRepositoryImpl(DogResponseItemDatabase(this))
        val viewModelProviderFactory = DogsViewModelProviderFactory(application, dogRepository)
        dogViewModel = ViewModelProvider(this, viewModelProviderFactory)[DogsViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dogsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        Log.d(TAG, "MainActivity is created.")
    }
}