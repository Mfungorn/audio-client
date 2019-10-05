package org.fungorn.audio.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_author.*
import org.fungorn.audio.R
import org.koin.android.viewmodel.ext.android.viewModel


class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_author, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // setup listeners
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.text.observe(this, Observer {
            authorName.text = it
        })
    }
}