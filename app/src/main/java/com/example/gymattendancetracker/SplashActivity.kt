package com.example.gymattendancetracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(2000)
            auth  = Firebase.auth //FirebaseAuth.getInstance()

            if(auth.currentUser!=null){
                val intent = Intent(this@SplashActivity , MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                val intent = Intent(this@SplashActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}