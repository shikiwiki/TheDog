package com.example.thedog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.local.DogResponseItemDatabase
import com.example.data.repository.DogRepositoryImpl
import com.example.thedog.databinding.ActivityMainBinding
import com.example.thedog.dogs.DogsViewModel

class MainActivity : AppCompatActivity() {

    lateinit var dogViewModel: DogsViewModel
    lateinit var binding: ActivityMainBinding

//    private val savedButton: Button by lazy { findViewById(R.id.floatingActionButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        val dogRepository = DogRepositoryImpl(DogResponseItemDatabase(this))
        val viewModelProviderFactory = DogsViewModelProviderFactory(application, dogRepository)
        dogViewModel = ViewModelProvider(this, viewModelProviderFactory).get(DogsViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dogsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}