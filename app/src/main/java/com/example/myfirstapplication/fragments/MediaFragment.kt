package com.example.myfirstapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.myfirstapplication.R


class MediaFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_media, container, false)
        handleAlbumList(root)
        return root;
    }

    private fun handleAlbumList(root : View) {
        val chefRebelButton = root.findViewById<CardView>(R.id.chef_rebel)
        chefRebelButton.setOnClickListener{
            view?.let {
                findNavController().navigate(R.id.album)
            }
        }
    }
}