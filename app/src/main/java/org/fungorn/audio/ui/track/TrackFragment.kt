package org.fungorn.audio.ui.track

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_track.*
import org.fungorn.audio.R
import org.koin.android.viewmodel.ext.android.viewModel


class TrackFragment : Fragment() {
    private val viewModel: TrackViewModel by viewModel()

    private var id: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        id = arguments?.getLong("track_id")
        id?.let {
            viewModel.getTrack(it)
            viewModel.checkIsFavorite(it)
        } ?: showToast("Cannot get Track Id")
        val root = inflater.inflate(R.layout.fragment_track, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        author.setOnClickListener {
            // TODO: Navigate Author by name
        }

        genre.setOnClickListener {
            // TODO: Navigate to genre track list (?)
        }

        buyTrackButton.setOnClickListener {
            // TODO: set BUY_TRACK request
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.track.observe(viewLifecycleOwner, Observer {
            Glide.with(this)
                .load(it.cover)
                .centerCrop()
                .fallback(R.drawable.ic_album_primary_150dp)
                .error(R.drawable.ic_album_primary_150dp)
                .into(trackImage)
            trackName.text = it.title
            trackText.text = it.text
            likes.text = it.rating.toString()
            author.text = it.authorName
            genre.text = it.genre
        })

        viewModel.isFavorite.observe(viewLifecycleOwner, Observer {
            it?.let { likeButton.isChecked = it }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    author.isEnabled = false
                    likeButton.isEnabled = false
                    buyTrackButton.isEnabled = false
                    progress.visibility = View.VISIBLE
                }
                false -> {
                    progress.visibility = View.GONE
                    author.isEnabled = true
                    likeButton.isEnabled = true
                    buyTrackButton.isEnabled = true
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

// TODO: init Tab in Author Fragment
//        fragment_author
//        tab.setup()
//        var ts = tab.newTabSpec("about")
//        ts.setContent(R.id.about)
//        ts.setIndicator("about")
//        tab.addTab(ts)
//        ts = tab.newTabSpec("tracks")
//        ts.setContent(R.id.tracks)
//        ts.setIndicator("tracks")
//        tab.addTab(ts)
//        ts = tab.newTabSpec("albums")
//        ts.setContent(R.id.albums)
//        ts.setIndicator("albums")
//        tab.addTab(ts)