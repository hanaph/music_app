package com.example.myfirstapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.wakandatech.djaniialfa.adapters.AlbumAdapter
import com.wakandatech.djaniialfa.models.Song
import com.wakandatech.djaniialfa.utils.ChefRebel


class AlbumFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_album, container, false)

        val songs : List<Song> = ChefRebel
        val adapter : AlbumAdapter = AlbumAdapter(songs)

        val recyclerView =root.findViewById<RecyclerView>(R.id.song_list)
        recyclerView.adapter = adapter

        return root;
    }
}