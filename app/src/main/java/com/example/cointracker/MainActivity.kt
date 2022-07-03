package com.example.cointracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.cointracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentcontainerView)
        val navcontroller=navHostFragment!!.findNavController()

        val popmenu=PopupMenu(this,null)
        popmenu.inflate(R.menu.bottom_nav_menu)
        binding.bottomBar.setupWithNavController(popmenu.menu,navcontroller)

    }
}