package org.fungorn.audio.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_track.view.*
import org.fungorn.audio.R
import org.fungorn.audio.domain.model.Track

class TrackAdapter(
    private val onItemClick: (Track) -> Unit
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    init {
        differ.submitList(listOf())
    }

    fun submitTracks(tracks: List<Track>) {
        differ.submitList(tracks)
        notifyDataSetChanged()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val track = differ.currentList[position]
        holder.bind(track)
    }

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var isTextHidden = true

        fun bind(track: Track) = with(itemView) {
            trackName.text = track.title
            duration.text = String.format("%02d:%02d", track.duration / 60, track.duration % 60)
            closeTextButton.setOnClickListener {
                isTextHidden = true
                trackText.text = ""
                trackText.visibility = View.GONE
                closeTextButton.visibility = View.GONE
            }
            setOnLongClickListener {
                trackText.text = if (!isTextHidden) track.text else ""
                trackText.visibility = if (isTextHidden) View.GONE else View.VISIBLE
                closeTextButton.visibility = if (isTextHidden) View.GONE else View.VISIBLE
                isTextHidden = !isTextHidden
                true
            }
            setOnClickListener {
                onItemClick(track)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Track>() {
            override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
                return oldItem == newItem
            }
        }
    }
}