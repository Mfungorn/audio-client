package org.fungorn.audio.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_signup.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.utils.EditTextWatcher
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_signup, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText.addTextChangedListener(EditTextWatcher { s, _, _, _ ->
            viewModel.signUpRequest.name = s.toString()
        })

        passwordEditText.addTextChangedListener(EditTextWatcher { s, _, _, _ ->
            viewModel.signUpRequest.password = s.toString()
        })

        emailEditText.addTextChangedListener(EditTextWatcher { s, _, _, _ ->
            viewModel.signUpRequest.email = s.toString()
        })

        registerButton.setOnClickListener {
            viewModel.signUp()
        }

        cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.signedUpEvent.observe(this, Observer {
            findNavController().navigateUp()
        })

        viewModel.error.observe(this, Observer {
            it?.let { showToast(it) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}