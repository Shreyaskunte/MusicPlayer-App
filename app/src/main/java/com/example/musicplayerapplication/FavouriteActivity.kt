package com.example.musicplayerapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerapplication.R
import com.example.musicplayerapplication.databinding.ActivityFavouriteBinding

class FavouriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFavouriteBinding
    private lateinit var adapter: FavouriteAdapter

    companion object {
        var favouriteSongs: ArrayList<Music> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        setTheme(R.style.coolPink)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.favouriteRV.setHasFixedSize(true)
        binding.favouriteRV.setItemViewCacheSize(13)
        binding.favouriteRV.layoutManager = GridLayoutManager(this,4)
        adapter = FavouriteAdapter(this, favouriteSongs)
        binding.favouriteRV.adapter = adapter
        binding.deleteBtnFAV.setOnClickListener { finish() }
        adapter = FavouriteAdapter(this, favouriteSongs)
        binding.favouriteRV.adapter = adapter
        if(favouriteSongs.size < 1) binding.shuffleBtnFA.visibility = View.INVISIBLE
        binding.shuffleBtnFA.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "FavouriteShuffle")
            startActivity(intent)
        }
    }
}