package com.drccities.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.drccities.R
import com.drccities.models.CitiesData
import com.google.android.material.textview.MaterialTextView
import java.util.*
import kotlin.collections.ArrayList

class CitiesAdapter(val context: Context, val items: MutableList<CitiesData>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>(), Filterable {

    private var itemsAll: ArrayList<CitiesData> = ArrayList(items)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.cities, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bindItem(items[position])
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: MaterialTextView = itemView.findViewById(R.id.title_city)
        val country: MaterialTextView = itemView.findViewById(R.id.title_country)
        val lat: MaterialTextView = itemView.findViewById(R.id.lat)
        val lon: MaterialTextView = itemView.findViewById(R.id.lon)
        val map: RelativeLayout = itemView.findViewById(R.id.city)

        @SuppressLint("SetTextI18n")
        fun bindItem(data: CitiesData) {
            city.text = data.name + ","
            country.text = data.country
            lat.text = data.coord.lat.toString() + ","
            lon.text = data.coord.lon.toString()

            map.setOnClickListener {
                val uri: String = java.lang.String.format(
                    Locale.ENGLISH,
                    "geo:%f,%f",
                    data.coord.lat,
                    data.coord.lon
                )
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
            }
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = arrayListOf<CitiesData>()
            if (constraint.toString().isEmpty()) {
                filteredList.addAll(itemsAll)
            } else {
                val filterpattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (item in itemsAll) {
                    if (item.name.toLowerCase().startsWith(filterpattern)) {
                        filteredList.add(item)
                    } else if (item.country.startsWith(filterpattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val result = FilterResults()
            result.values = filteredList

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            items.clear()
            items.addAll(results?.values as Collection<CitiesData>)
            notifyDataSetChanged()
        }
    }
}