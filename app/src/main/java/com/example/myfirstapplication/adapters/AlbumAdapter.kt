package com.wakandatech.djaniialfa.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.wakandatech.djaniialfa.models.Song

class AlbumAdapter(private val song: List<Song>)
    : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        init {
            view.findViewById<CardView>(R.id.chef_rebel_item).setOnClickListener{
                navigateToPlay(view,cardView.tag.toString() )
            }
        }
        val textView : TextView = view.findViewById(R.id.title)
        val cardView : CardView = view.findViewById(R.id.chef_rebel_item)

        private fun navigateToPlay(view: View, id : String) {
            val bundle = bundleOf("id" to id)
            view.findNavController().navigate(R.id.playFragment,bundle)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.album_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = song[position].name
        holder.cardView.tag = song[position].id
    }

    override fun getItemCount(): Int {
        return song.size
    }

}