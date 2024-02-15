package com.example.thedog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val logo: ImageView by lazy { findViewById(R.id.logo) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        logo.alpha = 0F
        logo.animate().setDuration(1500).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                overrideActivityTransition(
                    OVERRIDE_TRANSITION_OPEN,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    Color.TRANSPARENT
                )
            }
            finish()
        }
    }
}