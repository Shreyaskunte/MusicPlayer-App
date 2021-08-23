package com.example.musicplayerapplication

import android.net.wifi.p2p.WifiP2pManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayerapplication.databinding.ActivityPlayListBinding
import com.example.musicplayerapplication.databinding.AddPlaylistDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PlayListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPlayListBinding
    private lateinit var adapter : PlaylistViewAdapter

    companion object{
        var musicPlaylist: MusicPlaylist = MusicPlaylist()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_list)
        setTheme(R.style.coolPink)
        binding = ActivityPlayListBinding.inflate(layoutInflater)   //content view setting
        setContentView(binding.root)
        binding.playlistRV.setHasFixedSize(true)
        binding.playlistRV.setItemViewCacheSize(13)
        binding.playlistRV.layoutManager = GridLayoutManager(this,2)
        adapter = PlaylistViewAdapter(this , playlistList = musicPlaylist.ref)
        binding.playlistRV.adapter = adapter
        binding.addPlaylistButton.setOnClickListener {
            customAlertDialog()
        }
        binding.deleteBtnPlylst.setOnClickListener { finish() }
    }

    private fun customAlertDialog(){
        val customDialog = LayoutInflater.from(this,).inflate(R.layout.add_playlist_dialog,binding.root,false)
        val binder = AddPlaylistDialogBinding.bind(customDialog)
        val builder =   MaterialAlertDialogBuilder(this)
        builder.setView(customDialog)
            .setTitle("Playlist Details")
            .setPositiveButton("ADD"){ dialog,_->
                val playlistName = binder.playlistName.text
                val createdBy = binder.userName.text
                if (playlistName != null && createdBy != null)
                    if (playlistName.isNotEmpty() && createdBy.isNotEmpty())
                    {
                        addPlaylist(playlistName.toString(),createdBy.toString())
                    }
                dialog.dismiss()
            }.show()
    }

    private fun addPlaylist(name: String,createdBy: String){
        var playlistExists = false
        for (i in musicPlaylist.ref){
            if (name.equals(i.name)){
                playlistExists = true
            }
        }

        if(playlistExists){
            Toast.makeText(this,"Playlist Exists!!!",Toast.LENGTH_SHORT).show()
        }else{
            val tempPlaylist = PlayList()
            tempPlaylist.name = name
            tempPlaylist.playlist = ArrayList()
            tempPlaylist.createdBy = createdBy
            val calender = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd MMM yyyy",Locale.ENGLISH)
            tempPlaylist.createdOn = sdf.format(calender)
            musicPlaylist.ref.add(tempPlaylist)
            adapter.refreshPlaylist()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}