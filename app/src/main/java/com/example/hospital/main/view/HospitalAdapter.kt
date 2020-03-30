package com.example.hospital.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.hospital.R
import com.example.hospital.model.database.Hospital
import kotlinx.android.synthetic.main.info_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class HospitalAdapter(
    private val hospitals: MutableList<Hospital>,
    private val listener: (Hospital) -> Unit,
    private val nothingFoundLogic: () -> Unit
) :
    RecyclerView.Adapter<HospitalAdapter.ViewHolder>(), Filterable {

    private val hospitalListAll = ArrayList(hospitals)

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(position: Int) {
            itemView.tv_name_actual.text = hospitals[position].organisationName
            itemView.tv_city_actual.text = hospitals[position].city
            itemView.tv_phone_actual.text = hospitals[position].phone
            itemView.tv_website_actual.text = hospitals[position].website

            itemView.setOnClickListener {
                listener(hospitals[position])
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private val filterResults = FilterResults()

            //run on background thread
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                hospitals.clear()
                if (constraint.isNullOrBlank()) {
                    hospitals.addAll(hospitalListAll)
                } else {
                    for (item in hospitalListAll) {
                        if (item.organisationName.toLowerCase(Locale.ROOT).contains(constraint.toString().toLowerCase(Locale.ROOT))) {
                            hospitals.add(item)
                        }
                    }
                }
                return filterResults.also {
                    it.values = hospitals
                }
            }

            //runs on ui thread
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (hospitals.isNullOrEmpty())
                    nothingFoundLogic()
                notifyDataSetChanged()
            }
        }
    }
}