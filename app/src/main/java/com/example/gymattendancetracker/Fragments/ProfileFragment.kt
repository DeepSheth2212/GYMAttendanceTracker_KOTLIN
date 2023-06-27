package com.example.gymattendancetracker.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.gymattendancetracker.MainActivity
import com.example.gymattendancetracker.Model.User
import com.example.gymattendancetracker.R
import com.example.gymattendancetracker.SignInActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val logoutbtn = view.findViewById<Button>(R.id.LogOutBtn)
        val userName = view.findViewById<TextView>(R.id.userName)

        val userDbRef = FirebaseDatabase.getInstance().getReference("Users").child(Firebase.auth.currentUser!!.uid).child("name")

        userDbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userName.text = snapshot.value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                userName.text = error.message
            }
        })


        //logout button
        logoutbtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(activity,SignInActivity::class.java))
            requireActivity().finish()
        }

        return view
    }
}