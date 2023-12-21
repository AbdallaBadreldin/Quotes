package com.example.quotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.quotes.R
import pojo.Quotes
import storage.SharedPreferencesManager

class QuotesAdapter(private val quotes: List<Quotes>): RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item,parent,false)
        return QuotesViewHolder(view)

    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val quote = quotes[position]
        holder.favoriteQuotes.text = quote.content
        holder.removeQuotes.setOnClickListener {
        }
    }

    override fun getItemCount(): Int = quotes.size

    //inner class QuoteViewHolder
    class QuotesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val favoriteQuotes = itemView.findViewById<TextView>(R.id.text_fav_quote)

        val removeQuotes = itemView.findViewById<Button>(R.id.btn_remove_quote)
    }
}

