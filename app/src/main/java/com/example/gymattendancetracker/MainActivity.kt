package com.example.gymattendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.gymattendancetracker.Fragments.HomeFragment
import com.example.gymattendancetracker.Fragments.ProfileFragment
import com.example.gymattendancetracker.Fragments.QRFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth  = FirebaseAuth.getInstance()
        replace(HomeFragment())




        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId){
                R.id.home_menu -> {
                    replace(HomeFragment())
                    true
                }
                R.id.profile_menu -> {
                    replace(ProfileFragment())
                    true
                }
                R.id.qr_menu -> {
                    replace(QRFragment())
                    true
                }
                else -> true
            }
        }
    }



    private fun replace(fragment : Fragment){
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout,fragment)
        transaction.commit()
    }






}