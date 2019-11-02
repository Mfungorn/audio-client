package org.fungorn.audio.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_genre.view.*
import org.fungorn.audio.R
import org.fungorn.audio.domain.model.Genre
import kotlin.random.Random

class GenreAdapter(
    private val onItemClick: (Genre) -> Unit
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    init {
        differ.submitList(listOf())
    }

    private val colors = arrayOf(
        R.color.colorAmber,
        R.color.colorGrey,
        R.color.colorLightAmber,
        R.color.colorLightBlue,
        R.color.colorLightGreen,
        R.color.colorRed,
        R.color.colorYellow
    )

    fun submitGenres(genres: List<Genre>) {
        differ.submitList(genres)
        notifyDataSetChanged()
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = differ.currentList[position]
        holder.bind(genre)
    }


    inner class GenreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(genre: Genre) = with(itemView) {
            genreName.text = genre.name
            genreBack.setBackgroundResource(colors[Random.nextInt(colors.size)])
            setOnClickListener { onItemClick(genre) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
                return oldItem == newItem
            }
        }
    }
}