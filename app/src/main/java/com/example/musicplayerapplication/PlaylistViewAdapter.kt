package com.example.musicplayerapplication

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicplayerapplication.databinding.FavouriteViewBinding
import com.example.musicplayerapplication.databinding.PlaylistViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PlaylistViewAdapter(private var context : Context, private var playlistList : ArrayList<PlayList>) : RecyclerView.Adapter<PlaylistViewAdapter.MyHolder>() {

    class MyHolder(binding: PlaylistViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.playlistImage
        val name = binding.playlistName
        val root = binding.root
        val delete = binding.playlistDeleteBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(PlaylistViewBinding.inflate(LayoutInflater.from(context) , parent , false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = playlistList[position].name
        holder.name.isSelected = true
        holder.delete.setOnClickListener {
            val builder = MaterialAlertDialogBuilder(context)
            builder.setTitle(playlistList[position].name)
                .setMessage("Do you want to delete playlist?")
                .setPositiveButton("Yes"){ dialog, _->
                        PlayListActivity.musicPlaylist.ref.removeAt(position)
                        refreshPlaylist()
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

        holder.root.setOnClickListener {
            val intent = Intent(context,PlaylistDetails::class.java)
            intent.putExtra("index",position)
            ContextCompat.startActivity(context,intent,null)
        }
    }

    override fun getItemCount(): Int {
        return playlistList.size
    }

    private fun sendIntent(ref : String,pos:Int){
        val intent = Intent(context,PlayerActivity::class.java)
        intent.putExtra("index",pos)
        intent.putExtra("class",ref)
        ContextCompat.startActivity(context,intent,null)
    }

    fun refreshPlaylist(){
        playlistList = ArrayList()
        playlistList.addAll(PlayListActivity.musicPlaylist.ref)
        notifyDataSetChanged()
    }

}