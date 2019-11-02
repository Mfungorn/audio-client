package org.fungorn.audio.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAuthors()
        viewModel.getAlbums()
        viewModel.getGenres()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorAdapter = AuthorAdapter {
            findNavController().navigate(R.id.authorFragment, bundleOf("author_id" to it.id))
        }
        authorsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = authorAdapter
            scrollBy(0, 0)
        }

        albumAdapter = AlbumAdapter {
            findNavController().navigate(R.id.albumFragment, bundleOf("album_id" to it.id))
        }
        albumsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = albumAdapter
            scrollBy(0, 0)
        }

        genreAdapter = GenreAdapter {
            // TODO: Handle on genre click (?)
        }
        genresList.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = genreAdapter
            scrollBy(0, 0)
        }

        profileButton.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
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

        viewModel.isAuthorsLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    authorsProgress.visibility = View.VISIBLE
                }
                false -> {
                    authorsProgress.visibility = View.GONE
                    authorsList.scrollBy(0, 0)
                }
            }
        })

        viewModel.isAlbumsLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    albumsProgress.visibility = View.VISIBLE
                }
                false -> {
                    albumsProgress.visibility = View.GONE
                    albumsList.scrollBy(0, 0)
                }
            }
        })

        viewModel.isGenresLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    genresProgress.visibility = View.VISIBLE
                }
                false -> {
                    genresProgress.visibility = View.GONE
                    genresList.scrollBy(0, 0)
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