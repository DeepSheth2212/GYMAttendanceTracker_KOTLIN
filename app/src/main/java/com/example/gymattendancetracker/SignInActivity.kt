package com.example.gymattendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth  = Firebase.auth //FirebaseAuth.getInstance()

//        if(auth.currentUser!=null){
//            startActivity(Intent(this , MainActivity::class.java))
//        }

        SignIn.setOnClickListener {
            val email = EmailSI.text.toString()
            val password = PasswordSI.text.toString()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"User Logged In!",Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        startActivity(Intent(this,MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Sign In failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

        ToSU.setOnClickListener {
            startActivity(Intent(this , SignUpActivity::class.java))
        }

    }
}