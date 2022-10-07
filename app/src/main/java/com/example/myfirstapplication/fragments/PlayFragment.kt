package com.example.myfirstapplication.fragments

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.myfirstapplication.R
import com.wakandatech.djaniialfa.utils.ChefRebel


class PlayFragment : Fragment() {
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0
    private lateinit var positionBar: SeekBar
    private lateinit var playBtn : Button
    private lateinit var backBtn : Button
    private lateinit var nextBtn : Button
    private lateinit var songTitle : TextView
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_play, container, false)

        val id = arguments?.getString("id")?.toInt()
        val uri: Int = ChefRebel[id!!].uri
        val name = ChefRebel[id].name
        songTitle = root.findViewById(R.id.song_name)
        songTitle.text = name
        mp = MediaPlayer.create(context, uri)

        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        playBtn = root.findViewById<Button>(R.id.playBtn)
        backBtn = root.findViewById<Button>(R.id.backBtn)
        nextBtn = root.findViewById<Button>(R.id.nextBtn)
        playBtnClick(root)
        playBtn.setOnClickListener { playBtnClick(root) }
        backBtn.setOnClickListener {
            val idForPass = id - 1
            val bundle = bundleOf("id" to idForPass.toString())
            root.findNavController().navigate(R.id.playFragment,bundle)
        }

        nextBtn.setOnClickListener {
            val idForPass = id + 1
            val bundle = bundleOf("id" to idForPass.toString())
            root.findNavController().navigate(R.id.playFragment,bundle)
        }

        positionBar = root.findViewById<SeekBar>(R.id.positionBar)
        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
            }
        }).start()

        return root
    }


    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPostion = msg.what

            // Update positionbar
            positionBar.progress = currentPostion

            // Update labels
            var elspedTime = createTimeLable(currentPostion)
            val elapsedTimeLabel = root.findViewById<TextView>(R.id.elapsedTimeLabel)
            elapsedTimeLabel.text = elspedTime

            var remainingTime = createTimeLable(totalTime)
            val remainingTimeLabel = root.findViewById<TextView>(R.id.remainingTimeLabel)
            remainingTimeLabel.text = "$remainingTime"
        }
    }

    fun createTimeLable(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"

        timeLabel += sec

        return timeLabel
    }

    fun playBtnClick(v: View) {

        if (mp.isPlaying) {
            //stop
            mp.pause()
            val playBtn = root.findViewById<Button>(R.id.playBtn)
            playBtn.setBackgroundResource(R.drawable.play)
        } else {
            //start
            mp.start()
            playBtn.setBackgroundResource(R.drawable.pause)
        }
    }

    fun distroyPreviousFragment() {
        val fragment = fragmentManager?.fragments
        if (fragment != null) {
            fragmentManager?.beginTransaction()?.remove(fragment[fragment.size-1])?.commit()
        }
    }

    override fun onStop() {
        mp.stop()
        super.onStop()
    }
}