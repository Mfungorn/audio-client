package org.fungorn.audio.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.AuthorAdapter
import org.koin.android.viewmodel.ext.android.viewModel


class FavoritesFragment : Fragment() {
    private val viewModel: FavoritesViewModel by viewModel()

    private var authorAdapter: AuthorAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.loadFavorites()
        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authorAdapter = AuthorAdapter {
            findNavController().navigate(R.id.authorFragment, bundleOf("author_id" to it.id))
        }
        favAuthorsList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = authorAdapter
        }

        profile.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }

        favTracksButton.setOnClickListener {
            findNavController().navigate(R.id.favoriteTracksListFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.authors.observe(viewLifecycleOwner, Observer {
            authorAdapter?.submitAuthors(it)
        })

        viewModel.tracks.observe(viewLifecycleOwner, Observer {
            trackCount.text = it.size.toString()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    profile.isEnabled = false
                    favTracksButton.isEnabled = false
                    progress.visibility = View.VISIBLE
                }
                false -> {
                    progress.visibility = View.GONE
                    profile.isEnabled = true
                    favTracksButton.isEnabled = true
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