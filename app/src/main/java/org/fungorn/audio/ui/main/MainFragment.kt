package org.fungorn.audio.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.AlbumAdapter
import org.fungorn.audio.ui.utils.AuthorAdapter
import org.fungorn.audio.ui.utils.GenreAdapter
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private var authorAdapter: AuthorAdapter? = null
    private var albumAdapter: AlbumAdapter? = null
    private var genreAdapter: GenreAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getContent() // content()
        val root = inflater.inflate(R.layout.fragment_main, container, false)
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

        albumAdapter = AlbumAdapter {
            // TODO: Navigation to Album
        }
        albumsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumAdapter
        }

        genreAdapter = GenreAdapter {
            // TODO: Handle on genre click
        }
        genresList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
        }

        profileButton.setOnClickListener {
            // TODO: Navigation to Profile
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.authors.observe(viewLifecycleOwner, Observer {
            authorAdapter?.submitAuthors(it)
        })

        viewModel.albums.observe(viewLifecycleOwner, Observer {
            albumAdapter?.submitAlbums(it)
        })

        viewModel.genres.observe(viewLifecycleOwner, Observer {
            genreAdapter?.submitGenres(it)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    progress.visibility = View.VISIBLE
                }
                false -> {
                    progress.visibility = View.GONE
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