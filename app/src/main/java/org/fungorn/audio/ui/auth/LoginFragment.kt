package org.fungorn.audio.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_login, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email.setText(viewModel.signUpRequest.name)

        signinButton.setOnClickListener {
            viewModel.signIn(
                email.text.toString(),
                password.text.toString()
            )
        }

        noAccountButton.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.signedInEvent.observe(this, Observer {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        })

        viewModel.error.observe(this, Observer {
            it?.let { showToast(it) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}