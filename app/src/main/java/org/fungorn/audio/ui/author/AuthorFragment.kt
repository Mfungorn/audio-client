package org.fungorn.audio.ui.author

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
import kotlinx.android.synthetic.main.fragment_author.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.AlbumAdapter
import org.fungorn.audio.ui.utils.TrackAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class AuthorFragment : Fragment() {
    private val viewModel: AuthorViewModel by viewModel()

    private var albumAdapter: AlbumAdapter? = null
    private var trackAdapter: TrackAdapter? = null

    private var id: Long? = null
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getLong("author_id")
        id?.let {
            viewModel.getAuthor(it)
            viewModel.checkIsFavorite(it)

            viewModel.getAuthorTracks(it)
            viewModel.getAuthorAlbums(it)
        }
        name = arguments?.getString("author_name")
        name?.let {
            viewModel.getAuthor(it)
            viewModel.checkIsFavorite(it)
        }

        val root = inflater.inflate(R.layout.fragment_author, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tab.setup()
        var ts = tab.newTabSpec("about")
        ts.setContent(R.id.about)
        ts.setIndicator("about")
        tab.addTab(ts)
        ts = tab.newTabSpec("tracks")
        ts.setContent(R.id.tracks)
        ts.setIndicator("tracks")
        tab.addTab(ts)
        ts = tab.newTabSpec("albums")
        ts.setContent(R.id.albums)
        ts.setIndicator("albums")
        tab.addTab(ts)

        albumAdapter = AlbumAdapter {
            findNavController().navigate(R.id.albumFragment, bundleOf("album_id" to it.id))
        }
        albumsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumAdapter
        }

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
                    "type" to "author"
                )
            )
        }
        likeButton.setOnCheckedChangeListener { _, b ->
            val like = likes.text.toString().toInt(10)
            if (b) {
                likes.text = (like + 1).toString()
            } else {
                likes.text = (like - 1).toString()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.author.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.logo)
                .centerCrop()
                .fallback(R.drawable.rect)
                .error(R.drawable.rect)
                .into(authorImage)
            authorName.text = it.name
            likes.text = it.rating.toString()
            bio.text = it.bio
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            likeButton.isChecked = it ?: false
        })

        viewModel.albums.observe(viewLifecycleOwner, Observer {
            albumAdapter?.submitAlbums(it)
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            trackAdapter?.submitTracks(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    likeButton.isEnabled = false
                    authorProgress.visibility = View.VISIBLE
                }
                false -> {
                    authorProgress.visibility = View.GONE
                    likeButton.isEnabled = true
                }
            }
        })

        viewModel.isTracksLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    likeButton.isEnabled = false
                    tracksProgress.visibility = View.VISIBLE
                }
                false -> {
                    tracksProgress.visibility = View.GONE
                    likeButton.isEnabled = true
                }
            }
        })

        viewModel.isAlbumsLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    likeButton.isEnabled = false
                    albumsProgress.visibility = View.VISIBLE
                }
                false -> {
                    albumsProgress.visibility = View.GONE
                    likeButton.isEnabled = true
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