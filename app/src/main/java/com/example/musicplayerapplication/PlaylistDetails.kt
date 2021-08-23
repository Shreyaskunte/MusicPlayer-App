package com.example.musicplayerapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayerapplication.databinding.ActivityPlayListBinding
import com.example.musicplayerapplication.databinding.ActivityPlaylistDetailsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder

class PlaylistDetails : AppCompatActivity() {

    lateinit var binding: ActivityPlaylistDetailsBinding
    lateinit var adapter: MusicAdapter

    companion object{
        var currentPlaylistPos: Int = -1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding  = ActivityPlaylistDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentPlaylistPos = intent.extras?.get("index") as Int
        binding.playlistDetailsRV.setItemViewCacheSize(10)
        binding.playlistDetailsRV.setHasFixedSize(true)
        binding.playlistDetailsRV.layoutManager = LinearLayoutManager(this)
        adapter = MusicAdapter(this,PlayListActivity.musicPlaylist.ref[currentPlaylistPos].playlist, playlistDetails = true)
        binding.playlistDetailsRV.adapter = adapter
        binding.backBtnPD.setOnClickListener { finish() }
        binding.addBtnPD.setOnClickListener {
            startActivity(Intent(this,SelectionActivity::class.java))
        }
        binding.backBtnPD.setOnClickListener { finish() }
        binding.removeBtnPD.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Remove")
                .setMessage("Do you want to remove all songs from playlist")
                .setPositiveButton("Yes"){ dialog, _->
                    PlayListActivity.musicPlaylist.ref[currentPlaylistPos].playlist.clear()
                    adapter.refreshPlaylists()
                    dialog.dismiss()
                }

                .setNegativeButton("No"){ dialog, _->
                    dialog.dismiss()
                }

            val customDialog = builder.create()
            customDialog.show()
            customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
            customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        }
        }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding.playlistNamePD.text = PlayListActivity.musicPlaylist.ref[currentPlaylistPos].name
        binding.moreInfoPD.text = "Total ${adapter.itemCount} Songs.\n\n" +
                "Created On:\n${PlayListActivity.musicPlaylist.ref[currentPlaylistPos].createdOn}\n\n" +
                " -- ${PlayListActivity.musicPlaylist.ref[currentPlaylistPos].createdBy}"
        if (adapter.itemCount > 0){
            Glide.with(this).
            load(PlayListActivity.musicPlaylist.ref[currentPlaylistPos].playlist[0].artUri)
                .apply(RequestOptions().placeholder(R.mipmap.music_player_icon).centerCrop())
                .into(binding.playlistImgPD)
        }

        val editor = getSharedPreferences("Favourites", MODE_PRIVATE).edit()
        val jsonString = GsonBuilder().create().toJson(FavouriteActivity.favouriteSongs)
        editor.putString("FavouriteSongs", jsonString)
        val jsonStringPlaylist = GsonBuilder().create().toJson(PlayListActivity.musicPlaylist)
        editor.putString("MusicPlaylist", jsonStringPlaylist)
        editor.apply()
    }
}