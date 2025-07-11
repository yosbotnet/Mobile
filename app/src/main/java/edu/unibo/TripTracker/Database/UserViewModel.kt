package edu.unibo.tracker.Database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)
    }

    fun getUserByEmail(email: String): Flow<User?> = repository.getUserByEmail(email)

    fun updateUser(user: User) = viewModelScope.launch {
        repository.updateUser(user)
    }
}