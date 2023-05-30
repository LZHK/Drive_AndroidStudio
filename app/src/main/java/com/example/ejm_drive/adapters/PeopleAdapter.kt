package com.example.ejm_drive.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ejm_drive.R
import com.example.ejm_drive.models.People

class PeopleAdapter(private val context: Context, private val peopleList: List<People>) :
    RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false)
        return PeopleViewHolder(view)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val person = peopleList[position]

        val fullName = "${person.name} ${person.surname}"
        holder.tId.text = person.id
        holder.tFullName.text = fullName
        holder.tAge.text = person.age
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tId: TextView = itemView.findViewById(R.id.tvId)
        val tFullName: TextView = itemView.findViewById(R.id.tvFullName)
        val tAge: TextView = itemView.findViewById(R.id.tvAge)
    }
}
