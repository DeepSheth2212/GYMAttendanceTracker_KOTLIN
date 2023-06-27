package com.example.gymattendancetracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gymattendancetracker.Model.Trainer
import com.example.gymattendancetracker.R
import com.example.gymattendancetracker.TrainersListAdapter
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var counter: TextView
    private lateinit var counterDbRef: DatabaseReference
    private lateinit var trainersDbRef : DatabaseReference
    private lateinit var trainersListAdapter: TrainersListAdapter
    private var trainerList = mutableListOf<Trainer>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        counter = view.findViewById<TextView>(R.id.counter)
        val trainersListView = view.findViewById<RecyclerView>(R.id.trainers_list_view)
        trainersListView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        trainersListAdapter = TrainersListAdapter(trainerList)
        trainersListView.adapter = trainersListAdapter

        updateList()


        //for counter
        counterDbRef = FirebaseDatabase.getInstance().getReference("Data").child("LiveCounter")
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

        counterDbRef.addValueEventListener(postListener)

        return view
    }

    private fun updateList() {
        trainersDbRef = FirebaseDatabase.getInstance().getReference("Trainers")
        val postListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                trainerList.clear()

                for(childSnapshot in snapshot.children){
                    val trainer = childSnapshot.getValue(Trainer::class.java)
                    trainer?.let{
                        if(it.isLive){
                            trainerList.add(it)
                        }
                    }
                }
                trainersListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity,"There might be some error!",Toast.LENGTH_SHORT).show()
            }
        }
        trainersDbRef.addValueEventListener(postListener)

    }


}