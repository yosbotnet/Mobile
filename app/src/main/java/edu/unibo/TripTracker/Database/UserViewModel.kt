package edu.unibo.tracker.Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun getUserByEmail(email: String, onResult: (User?) -> Unit) = viewModelScope.launch {
        onResult(userRepository.getUserByEmail(email))
    }
}