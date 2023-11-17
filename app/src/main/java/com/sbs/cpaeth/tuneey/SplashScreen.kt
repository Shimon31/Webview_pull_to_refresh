package com.sbs.cpaeth.tuneey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.sbs.cpaeth.tuneey.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    private val splashTime: Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Initialize()


        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, splashTime)
    }
    private fun Initialize() {
        var appLogo = binding.logo

        val topAnim = AnimationUtils.loadAnimation(
            applicationContext,
            androidx.constraintlayout.widget.R.anim.abc_slide_in_top
        )
        appLogo.startAnimation(topAnim)

    }


}