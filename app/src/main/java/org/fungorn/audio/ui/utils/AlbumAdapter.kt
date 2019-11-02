package org.fungorn.audio.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album.view.*
import org.fungorn.audio.R
import org.fungorn.audio.domain.model.Album

class AlbumAdapter(
    private val onItemClick: (Album) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    init {
        differ.submitList(listOf())
    }

    fun submitAlbums(albums: List<Album>) {
        differ.submitList(albums)
        notifyDataSetChanged()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = differ.currentList[position]
        holder.bind(album, position)
    }


    inner class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(album: Album, pos: Int) = with(itemView) {
            Glide.with(this)
                .load(album.cover)
                .placeholder(
                    if (pos % 2 == 0)
                        R.drawable.ic_album_black_64dp
                    else
                        R.drawable.ic_album_primary_64dp
                )
                .fallback(R.drawable.ic_album_grey_64dp)
                .centerCrop()
                .into(albumCover)
            albumTitle.text = album.title
            albumAuthor.text = album.authorName.toString()
            setOnClickListener { onItemClick(album) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem == newItem
            }
        }
    }
}