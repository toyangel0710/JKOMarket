package com.james.jkomarket.account.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.james.jkomarket.MainActivity
import com.james.jkomarket.R
import com.james.jkomarket.account.model.User
import com.james.jkomarket.account.viewmodel.UserViewModel
import com.james.jkomarket.utils.KeyboardUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        signupButton.setOnClickListener {
            if (TextUtils.isEmpty(userName.text)) {
                // TODO Handle wrong input statements.

            } else {
                // Hide keyboard
                KeyboardUtils.hideKeyboard(it)

                val user = User(
                    0,
                    userName.text.toString()
                )
                userViewModel.setUser(user)
            }
        }

        userViewModel.signupResult.observe(this, Observer {
            Toast.makeText(this, getString(it.message!!), Toast.LENGTH_SHORT).show()
            if (it.isSuccessed) {
                // Signup success
                openMain()
            } else {
                // Signup failed
                // TODO Clear edittext text or do something else
            }
        })

    }

    private fun openMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
