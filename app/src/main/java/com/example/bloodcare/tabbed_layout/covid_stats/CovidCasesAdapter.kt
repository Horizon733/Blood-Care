package com.example.bloodcare.tabbed_layout.covid_stats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bloodcare.R
import com.example.bloodcare.databinding.CovidItemBinding

class CovidCasesAdapter(internal var context: Context,internal var list:List<Data.Statewise>): RecyclerView.Adapter<CovidCasesAdapter.CovidCasesHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CovidCasesAdapter.CovidCasesHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.covid_item,parent,false)
        return CovidCasesHolder(view)
    }

    override fun onBindViewHolder(holder: CovidCasesAdapter.CovidCasesHolder, position: Int) {
        val state = list.get(position)
        holder.state.setText(state.state)
        holder.cases.setText("Cases: ${state.active}")
        holder.deaths.setText("Deaths: ${state.deaths}")
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class CovidCasesHolder(v:View): RecyclerView.ViewHolder(v) {
        val state = v.findViewById<TextView>(R.id.state)
        val deaths = v.findViewById<TextView>(R.id.deaths)
        val cases = v.findViewById<TextView>(R.id.cases)
    }
}