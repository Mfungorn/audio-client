package org.fungorn.audio.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.LoginActivity
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getUser()
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        addBalance.setOnClickListener {
            context?.let { context ->
                AlertDialog.Builder(context)
                    .setTitle("Account dialog")
                    .setMessage("Do you want to deposit your account?")
                    .setPositiveButton("Proceed") { dialog, _ -> dialog.dismiss() }
                    .setNegativeButton("Abort") { dialog, _ -> dialog.dismiss() }
                    .setIcon(R.drawable.ic_add_balance_accent_24dp)
                    .show()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.user.observe(viewLifecycleOwner, Observer {
            username.text = it.name
            userEmail.text = it.email
            Glide.with(this)
                .load(it.icon)
                .centerCrop()
                .fallback(R.drawable.ic_face_black_128dp)
                .error(R.drawable.ic_face_black_128dp)
                .into(profileImage)
            balance.text = it.balance.toString()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    logout.isEnabled = false
                    addBalance.isEnabled = false
                }
                false -> {
                    logout.isEnabled = true
                    addBalance.isEnabled = true
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