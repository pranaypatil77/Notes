package com.example.roomdb.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdb.Db.Notes
import com.example.roomdb.R
import com.example.roomdb.Ui.HomeFragmentDirections
import kotlinx.android.synthetic.main.notes_item.view.*

class NotesAdapter (var data : List<Notes>): RecyclerView.Adapter<NotesAdapter.ViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.noteTitle.text = data[position].title
        holder.itemView.noteDes.text = data[position].notes
        val i = data.get(position)
        holder.itemView.setOnClickListener {

            holder.itemView.card.setCardBackgroundColor(Color.RED)
            val action = HomeFragmentDirections.actionAddNote()
            action.note = data[position]
            Navigation.findNavController(it).navigate(action)
        }
    }
    class ViewHolder (item :View) :RecyclerView.ViewHolder(item){

    }
}