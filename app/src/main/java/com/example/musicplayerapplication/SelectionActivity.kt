package com.example.musicplayerapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerapplication.databinding.ActivityPlaylistDetailsBinding
import com.example.musicplayerapplication.databinding.ActivitySelectionBinding

class SelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectionBinding
    lateinit var adapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.selectionRV.setItemViewCacheSize(10)
        binding.selectionRV.setHasFixedSize(true)
        binding.selectionRV.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this,SongsActivity.MusicListMA, selectionActivity = true)
        binding.selectionRV.adapter = adapter
        binding.backBtnSL.setOnClickListener { finish() }

        binding.searchViewSA.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true
            override fun onQueryTextChange(newText: String?): Boolean {
                SongsActivity.musicListSearch = ArrayList()
                if(newText != null) {
                    val userInput = newText.lowercase()
                    for (song in SongsActivity.MusicListMA)
                        if(song.title.lowercase().contains(userInput))
                            SongsActivity.musicListSearch.add(song)
                    SongsActivity.search = true
                    adapter.updateMusicList(searchList = SongsActivity.musicListSearch)
                }
                return true
            }
        })
    }
}