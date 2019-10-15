package org.fungorn.audio.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_album.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.TrackAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class AlbumFragment : Fragment() {
    private val viewModel: AlbumViewModel by viewModel()

    private var trackAdapter: TrackAdapter? = null

    private var id: Long? = null
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getLong("album_id")
        id?.let {
            viewModel.getAlbum(it)
            viewModel.getContent(it)
        }
        name = arguments?.getString("album_name")
        name?.let {
            viewModel.getAlbum(it)
        }

        val root = inflater.inflate(R.layout.fragment_album, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter = TrackAdapter {
            findNavController().navigate(R.id.trackFragment, bundleOf("track_id" to it.id))
        }
        tracksList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trackAdapter
        }

        genre.setOnClickListener {
            val name = (it as TextView).text.toString()
            findNavController().navigate(
                R.id.genresFragment,
                bundleOf(
                    "genre_name" to name,
                    "type" to "album"
                )
            )
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.album.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.cover)
                .centerCrop()
                .fallback(R.drawable.ic_album_primary_150dp)
                .error(R.drawable.ic_album_primary_150dp)
                .into(albumImage)
            albumName.text = it.title
            likes.text = it.rating.toString()
            author.text = it.authorName
            tracks.text = "(${it.tracksCount})"
            genre.text = it.genre
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            trackAdapter?.submitTracks(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    likeButton.isEnabled = false
                    progress.visibility = View.VISIBLE
                }
                false -> {
                    likeButton.isEnabled = true
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            showToast(it.message.toString())
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}