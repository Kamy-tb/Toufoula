package com.example.menuapplication

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.READ_PHONE_STATE
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.menuapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    val LOCATION_PERMISSION_REQUEST_CODE = 1
    val PHONE_PERMISSION_REQUEST_CODE = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager. findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.navBottom,navController)

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.reglementation_dialog)
        dialog.setCancelable(false)

        val acceptButton = dialog.findViewById<Button>(R.id.accept_button)
        acceptButton.setOnClickListener {
            // handle the user accepting the regulations
            dialog.dismiss()
        }

        val declineButton = dialog.findViewById<Button>(R.id.decline_button)
        declineButton.setOnClickListener {
            // handle the user declining the regulations
            finish()
        }


    }




}