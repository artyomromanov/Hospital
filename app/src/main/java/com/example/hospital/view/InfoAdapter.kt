package com.example.hospital.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.model.database.Hospital
import com.example.hospital.R
import kotlinx.android.synthetic.main.info_item.view.*

class InfoAdapter(private val hospitals : List<Hospital>) : RecyclerView.Adapter<InfoAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val v = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return hospitals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int){
            itemView.tv_name_actual.text = hospitals[position].organisationName
            itemView.tv_city_actual.text = hospitals[position].city
            itemView.tv_phone_actual.text = hospitals[position].phone
            itemView.tv_website_actual.text = hospitals[position].website
        }
    }
}