package org.fungorn.audio.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*
import org.fungorn.audio.R
import org.fungorn.audio.ui.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signinButton.setOnClickListener {
            viewModel.signIn(
                email.text.toString(),
                password.text.toString()
            )
        }

        viewModel.signedInEvent.observe(this, Observer {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        viewModel.error.observe(this, Observer {
            it?.let { showToast(it) }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}