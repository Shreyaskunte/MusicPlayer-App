package com.example.musicplayerapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayerapplication.databinding.ActivityPlayerBinding
import com.example.musicplayerapplication.databinding.FragmentNowPlayingBinding

class NowPlaying : Fragment() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE
        binding.playBtnNP.setOnClickListener {
            if(PlayerActivity.isPlaying) pauseMusic() else playMusic()
        }
        binding.nextBtnNP.setOnClickListener {
            setSongPosition(increment = true)
            PlayerActivity.musicService!!.createMediaPlayer()
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
                .into(binding.songImgNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
            playMusic()
        }
        binding.root.setOnClickListener {
            val intent = Intent(requireContext(), PlayerActivity::class.java)
            intent.putExtra("index", PlayerActivity.songPosition)
            intent.putExtra("class", "NowPlaying")
            ContextCompat.startActivity(requireContext(), intent, null)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if(PlayerActivity.musicService != null){
            binding.root.visibility = View.VISIBLE
            binding.songNameNP.isSelected = true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].artUri)
                .apply(RequestOptions().placeholder(R.drawable.splash_screen).centerCrop())
                .into(binding.songImgNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].title
            if(PlayerActivity.isPlaying) binding.playBtnNP.setIconResource(R.drawable.ic_baseline_pause_24)
            else binding.playBtnNP.setIconResource(R.drawable.ic_baseline_play_arrow_24)
        }
    }

    private fun playMusic(){
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        binding.playBtnNP.setIconResource(R.drawable.ic_baseline_pause_24)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_pause_24)
        PlayerActivity.binding.nextBtnPA.setImageResource(R.drawable.ic_baseline_pause_24)
        PlayerActivity.isPlaying = true
    }
    private fun pauseMusic(){
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        binding.playBtnNP.setIconResource(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.musicService!!.showNotification(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.binding.nextBtnPA.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        PlayerActivity.isPlaying = false
    }

}
