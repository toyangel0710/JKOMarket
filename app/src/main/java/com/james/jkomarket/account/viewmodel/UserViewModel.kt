package com.james.jkomarket.account.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.james.jkomarket.R
import com.james.jkomarket.account.SignupResult
import com.james.jkomarket.account.User
import com.james.jkomarket.account.UserState
import com.james.jkomarket.room.JkoMarketDatabase
import com.james.jkomarket.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    private val _signupResult = MutableLiveData<SignupResult>()
    val signupResult: LiveData<SignupResult> = _signupResult
    private lateinit var userState: UserState

    init {
        val userDao = JkoMarketDatabase.getDatabase(application, viewModelScope).userDao()
        repository = UserRepository(userDao)
        userState = UserState(application)
    }

    fun setUser(user: User) = viewModelScope.launch {
        try {
            val row: Long = repository.setUser(user)

            userState.isLoggin = true
            userState.userName = user.name
            // TODO Log user info in the future.


            // Set signup result
            _signupResult.value = SignupResult(true, R.string.success)

        } catch (exception: Exception) {
            // Set signup result
            _signupResult.value = SignupResult(false, R.string.signup_fail_username_exit)
        }
    }

    fun getUser(name: String) = viewModelScope.launch {
        repository.getUser(name)
    }
}