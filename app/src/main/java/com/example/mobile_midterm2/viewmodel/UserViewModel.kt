package com.example.mobile_midterm2.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile_midterm2.model.User
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface UserService {
    @GET("/users")
    suspend fun getUsers(): List<User>
}

class UserViewModel : ViewModel() {
    private val _userData = MutableLiveData<List<User>>()
    val userData: LiveData<List<User>> get() = _userData

    private val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @SuppressLint("LogNotTimber")
    fun fetchUserData() {
        viewModelScope.launch {
            try {
                val users = userService.getUsers()
                _userData.value = users
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching user data: ${e.message}")
            }
        }
    }
}
