package com.peseca.howmanypeopleareinspace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private var context: Context,
                          private var personNames: ArrayList<String>,
                          private var personPosition: ArrayList<String>,
                          private var personDaysInSpace: ArrayList<String>,
                          private var personWhereFrom: ArrayList<String>)
                        : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview, parent, false)
        return MyViewHolder(v)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = personNames[position]
        holder.position.text = personPosition[position]
        holder.wherefrom.text = personWhereFrom[position]
        holder.daysinspace.text = personDaysInSpace[position]
    }
    override fun getItemCount(): Int {
        return personNames.size
    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById<View>(R.id.nameofperson) as TextView
        var wherefrom: TextView = itemView.findViewById<View>(R.id.wherefrom) as TextView
        var position: TextView = itemView.findViewById<View>(R.id.position) as TextView
        var daysinspace: TextView = itemView.findViewById<View>(R.id.daysinspace) as TextView
    }
}