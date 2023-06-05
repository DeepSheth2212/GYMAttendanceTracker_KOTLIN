package com.example.gymattendancetracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.gymattendancetracker.R
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var counter : TextView
    private lateinit var dbref: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        counter = view.findViewById<TextView>(R.id.counter)
        dbref = FirebaseDatabase.getInstance().getReference("Data").child("LiveCounter")
        //Log.d("deep","dbref - $dbref")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = dataSnapshot.value
               // Log.d("deep","count: $count")
                counter.text = count.toString()
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("deep", "loadPost:onCancelled", databaseError.toException())
            }
        }

        dbref.addValueEventListener(postListener)

        return view
    }


}