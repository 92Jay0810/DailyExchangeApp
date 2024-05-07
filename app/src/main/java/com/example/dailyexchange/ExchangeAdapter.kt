package com.example.dailyexchange

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyexchange.database.exchange.Exchange
import com.example.dailyexchange.network.SingleForeignExchange

class ExchangeAdapter(private val dataset: List<Exchange>) : RecyclerView.Adapter<ExchangeAdapter.ViewHolder>() {


    class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val exchangeName: TextView = view.findViewById(R.id.exchangeName_textview)
        val exrate: TextView = view.findViewById(R.id.exrate_textview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item=dataset[position]
        holder.exchangeName.text = item.exchange
        holder.exrate.text=item.exrate.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size // Size problem
    }

}