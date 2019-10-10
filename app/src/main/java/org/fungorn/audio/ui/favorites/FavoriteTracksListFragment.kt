package org.fungorn.audio.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_fav_tracks.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.TrackAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteTracksListFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()

    private var trackAdapter: TrackAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadTracks()
        val root = inflater.inflate(R.layout.fragment_fav_tracks, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trackAdapter = TrackAdapter {
            // TODO: Navigation to track
        }
        favTracksList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trackAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            trackAdapter?.submitTracks(it)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            showToast(it.message.toString())
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}