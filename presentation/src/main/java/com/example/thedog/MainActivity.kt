package com.example.thedog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

//    private val savedButton: Button by lazy { findViewById(R.id.floatingActionButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

//        savedButton.setOnClickListener {
//            Log.d("SAVED_BTN", "Saved Button was clicked.")
//        }

//        val theDogViewModel = hiltViewModel<TheDogViewModel>()
    }
}