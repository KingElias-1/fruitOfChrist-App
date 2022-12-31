package com.kingelias.fruitofchrist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.kingelias.fruitofchrist.R
import com.kingelias.fruitofchrist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    var isHome = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.toolbar)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            isHome = when (destination.id) {
                R.id.addAuthorFragment -> false
                else -> true
            }
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {

        if (doubleBackToExitPressedOnce && isHome) {
            finishAffinity()
            finish()
            return
        }
        else if(isHome){
            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
        }else{
            super.onBackPressed()
        }
    }

}