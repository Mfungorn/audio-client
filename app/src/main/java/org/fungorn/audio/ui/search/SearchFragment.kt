package org.fungorn.audio.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.AuthorAdapter
import org.fungorn.audio.ui.utils.DebouncingQueryTextListener
import org.fungorn.audio.ui.utils.GenreAdapter
import org.fungorn.audio.ui.utils.TrackAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModel()

    private var authorAdapter: AuthorAdapter? = null
    private var trackAdapter: TrackAdapter? = null
    private var genreAdapter: GenreAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorAdapter = AuthorAdapter {
            // TODO: Navigation to Author
        }
        authorsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = authorAdapter
        }

        trackAdapter = TrackAdapter {
            // TODO: Navigation to Track
        }
        albumsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trackAdapter
        }

        genreAdapter = GenreAdapter {
            // TODO: Handle on genre click
        }
        genresList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }

        searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(viewLifecycleOwner.lifecycle) { newQuery ->
                newQuery?.let {
                    if (it.isEmpty()) {
                        viewModel.resetSearch()
                    } else {
                        viewModel.globalSearch(it)
                    }
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authors.observe(viewLifecycleOwner, Observer {
            authorAdapter?.submitAuthors(it)
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            trackAdapter?.submitTracks(it)
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer {
            genreAdapter?.submitGenres(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    searchView.isEnabled = false
                    progress.visibility = View.VISIBLE
                }
                false -> {
                    progress.visibility = View.GONE
                    searchView.isEnabled = true
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let { showToast(it.message.toString()) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}