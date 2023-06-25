package com.example.gymattendancetracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymattendancetracker.Model.Trainer
import com.example.gymattendancetracker.R
import com.example.gymattendancetracker.TrainersListAdapter
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
        val trainersListView = view.findViewById<RecyclerView>(R.id.trainers_list_view)
        trainersListView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        val trainersListAdapter = TrainersListAdapter(listOf(
            Trainer("Deep1"),
            Trainer("Deep2"),
            Trainer("Deep3"),
            Trainer("Deep4"),
            Trainer("Deep5"),
            Trainer("Deep6"),
            Trainer("Deep7")
        ))
        trainersListView.adapter = trainersListAdapter


        //for counter
        dbref = FirebaseDatabase.getInstance().getReference("Data").child("LiveCounter")
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