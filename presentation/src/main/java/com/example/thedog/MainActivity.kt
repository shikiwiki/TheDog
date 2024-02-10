package com.example.thedog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.data.local.db.DogDatabase
import com.example.data.local.repository.DogLocalRepository
import com.example.data.remote.network.RetrofitInstance
import com.example.data.remote.repository.DogRemoteRepository
import com.example.thedog.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    lateinit var dogViewModel: DogsViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DogDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Creating MainActivity")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DogDatabase.invoke(this.applicationContext)
        val localRepository = DogLocalRepository(db.getDao())
        val remoteRepository = DogRemoteRepository(RetrofitInstance.api)
        val viewModelProviderFactory =
            DogsViewModelProviderFactory(application, localRepository, remoteRepository)
        dogViewModel = ViewModelProvider(this, viewModelProviderFactory)[DogsViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.dogsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        Log.d(TAG, "MainActivity is created.")
    }
}