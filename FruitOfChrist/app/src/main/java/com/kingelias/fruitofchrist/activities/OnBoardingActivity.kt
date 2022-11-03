package com.kingelias.fruitofchrist.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kingelias.fruitofchrist.R

class OnBoardingActivity : AppCompatActivity() {
    private var isContentLoaded = true

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        //splash
        setupSplashScreen()

        //TODO(Making onBoarding Screen show up only on first time install)
        val preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        val firstTime: String? = preferences.getString("FirstTimeInstall", "")

        if (firstTime.equals("Yes", true)){
            //If app is not opened for the first time, it launches the main activity
            val launchAuth = Intent(this, AuthActivity::class.java)
            startActivity(launchAuth)
        }else{
            //If app is opened for the first time, it launches the onBoarding activity and sets the FirstTimeInstall preference to yes
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putString("FirstTimeInstall", "Yes")
            editor.apply()
        }
        //DONE


    }

    private fun setupSplashScreen() {
        // keep the splash for longer period -> say checking internet status/speed/, db setup

        // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isContentLoaded) {// Check if the initial data is ready.
                        // The content is ready; start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false  // hold on to the splash
                }
            }
        )
    }
}