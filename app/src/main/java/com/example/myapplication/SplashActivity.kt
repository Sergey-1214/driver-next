package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (hasInternet()) {
                openNextScreen()
            } else {
                startActivity(
                    Intent(this, NoInternetActivity::class.java)
                )
            }

            finish()
        }, 2500)
    }

    private fun openNextScreen() {
        val preferences =
            getSharedPreferences(
                "app_settings",
                MODE_PRIVATE
            )

        val onboardingCompleted =
            preferences.getBoolean(
                "onboarding_completed",
                false
            )

        if (onboardingCompleted) {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
        } else {
            startActivity(
                Intent(this, OnboardingActivity::class.java)
            )
        }

        finish()
    }
    private fun hasInternet(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false

        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}
