package com.example.gymattendancetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gymattendancetracker.Fragments.HomeFragment
import com.example.gymattendancetracker.Model.Trainer

class TrainersListAdapter(val trainersList: List<Trainer>) : Adapter<TrainersListAdapter.TrainersListViewHolder>() {
    class TrainersListViewHolder(itemView: View) : ViewHolder(itemView){
        val trainerName : TextView = itemView.findViewById(R.id.trainer_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.trainer_item_layout,parent,false)
        return TrainersListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainersList.size
    }

    override fun onBindViewHolder(holder: TrainersListViewHolder, position: Int) {
        holder.trainerName.text = trainersList[position].name
    }
}