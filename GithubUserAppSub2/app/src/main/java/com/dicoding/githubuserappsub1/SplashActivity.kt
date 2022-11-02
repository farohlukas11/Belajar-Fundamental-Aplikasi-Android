package com.dicoding.githubuserappsub1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.githubuserappsub1.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val delayTime = 2000L

        Handler(Looper.getMainLooper()).postDelayed({
            val intentSplash = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intentSplash)
            finish()
        }, delayTime)
    }
}