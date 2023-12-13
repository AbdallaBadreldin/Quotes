package com.example.quotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.quotes.R

class QuotesAdapter(private val quotes: List<String?>) :
    RecyclerView.Adapter<QuotesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favQuoteTextView: TextView = itemView.findViewById(R.id.text_fav_quote)
        val removeQuoteButton: Button = itemView.findViewById(R.id.btn_remove_quote)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quote = quotes[position]

        holder.favQuoteTextView.text = quote
        // Set click listener for your button if needed
        holder.removeQuoteButton.setOnClickListener {
            // Handle button click here
        }
    }
    override fun getItemCount()= quotes.size
}