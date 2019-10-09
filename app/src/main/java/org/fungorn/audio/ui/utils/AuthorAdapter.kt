package org.fungorn.audio.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.fungorn.audio.R
import org.fungorn.audio.domain.model.Author

class AuthorAdapter(
    private val onItemClick: (Author) -> Unit
) : RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder>() {
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitAuthors(authors: List<Author>) {
        differ.submitList(authors)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val viewItem: View = inflater.inflate(R.layout.item_author, parent, false)
        return AuthorViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val author = differ.currentList[position]
        holder.bind(author)
    }

    inner class AuthorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(author: Author) = with(itemView) {
            setOnClickListener { onItemClick(author) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Author>() {
            override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
                return oldItem == newItem
            }
        }
    }
}