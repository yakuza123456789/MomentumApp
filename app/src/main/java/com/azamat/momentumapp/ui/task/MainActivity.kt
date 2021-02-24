package com.azamat.momentumapp.ui.task

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.azamat.momentumapp.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
//        setupActionBarWithNavController(navController)
//        navController.navigate(R.id.action_firstFragment_to_splashScreenFragment)


    }

//    override fun onSupportNavigateUp(): Boolean {
//        return  navController.navigateUp() || super.onSupportNavigateUp()
//    }


}