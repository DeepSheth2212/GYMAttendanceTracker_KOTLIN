package com.example.gymattendancetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gymattendancetracker.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var dbref  = FirebaseDatabase.getInstance().getReference("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

        Register.setOnClickListener {
            val phone  = PhoneSU.text.toString()
            val name = nameSU.text.toString()
            val email = EmailSU.text.toString()
            val password = PasswordSU.text.toString()

            val user = User(name,phone,email,password)


            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val uid = auth.currentUser?.uid

                        //to save user's information in database
                        if (uid != null) {
                            dbref.child(uid).setValue(user).addOnCompleteListener { task ->
                                if(!task.isSuccessful){
                                    Log.d("SavingData","Error in saving user's data")
                                }
                            }
                        }
                        Toast.makeText(this, "User Created Successfully!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        ToSI.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }


}