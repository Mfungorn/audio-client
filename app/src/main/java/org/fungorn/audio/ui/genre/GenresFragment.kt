package org.fungorn.audio.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_genres.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.AlbumAdapter
import org.fungorn.audio.ui.utils.AuthorAdapter
import org.fungorn.audio.ui.utils.TrackAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class GenresFragment : Fragment() {
    private val viewModel: GenresViewModel by viewModel()

    private var authorAdapter: AuthorAdapter? = null
    private var albumAdapter: AlbumAdapter? = null
    private var trackAdapter: TrackAdapter? = null

    private var genreName: String? = ""
    private var type: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        genreName = arguments?.getString("genre_name")
        type = arguments?.getString("type")
        val root = inflater.inflate(R.layout.fragment_genres, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (type) {
            "author" -> {
                authorAdapter = AuthorAdapter {

                }
                typeList.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = authorAdapter
                }
                typeName.text = "Authors"
                genreName?.let { viewModel.loadAuthors(it) }
            }
            "album" -> {
                albumAdapter = AlbumAdapter {

                }
                typeList.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = albumAdapter
                }
                typeName.text = "Albums"
                genreName?.let { viewModel.loadAlbums(it) }
            }
            "track" -> {
                trackAdapter = TrackAdapter {

                }
                typeList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = trackAdapter
                }
                typeName.text = "Tracks"
                genreName?.let { viewModel.loadTracks(it) }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.authors.observe(viewLifecycleOwner, Observer {
            typeCount.text = it.size.toString()
            authorAdapter?.submitAuthors(it)
        })

        viewModel.albums.observe(viewLifecycleOwner, Observer {
            typeCount.text = it.size.toString()
            albumAdapter?.submitAlbums(it)
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            typeCount.text = it.size.toString()
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