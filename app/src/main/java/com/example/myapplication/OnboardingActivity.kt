package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_onboarding)
        val viewPager =
            findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = OnboardingAdapter(this)

        val skipButton =
            findViewById<Button>(R.id.skipButton)

        skipButton.setOnClickListener {
            finishOnboarding()
        }

        val nextButton =
            findViewById<Button>(R.id.nextButton)

        nextButton.setOnClickListener {
            if (viewPager.currentItem < 2) {
                viewPager.currentItem += 1
            } else {
                finishOnboarding()
            }
        }

        viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (position == 2) {
                        nextButton.text = "Поехали"
                    } else {
                        nextButton.text = "Далее"
                    }
                }
            }
        )
    }

    private fun finishOnboarding() {

        val preferences =
            getSharedPreferences(
                "app_settings",
                MODE_PRIVATE
            )

        preferences.edit()
            .putBoolean(
                "onboarding_completed",
                true
            )
            .apply()

        startActivity(
            Intent(this, MainActivity::class.java)
        )

        finish()
    }
}