package com.example.mobile_midterm2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile_midterm2.model.User
import com.example.mobile_midterm2.viewmodel.UserViewModel

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserList()
                }
            }
        }
    }
}

@Composable
fun UserList(userViewModel: com.example.mobile_midterm2.viewmodel.UserViewModel = viewModel()) {
    val userData by userViewModel.userData.observeAsState(emptyList())

    // Trigger API call when the composable is first launched
    LaunchedEffect(true) {
        userViewModel.fetchUserData()
    }

    // Display user data in a list format
    LazyColumn {
        items(userData) { user ->
            UserListItem(user)
        }
    }
}

@Composable
fun UserListItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Name: ${user.name} ${user.username}",
                style = typography.h6
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Email: ${user.email}",
                style = typography.body2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Phone: ${user.phone}",
                style = typography.body2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Address: ${user.address.street}, ${user.address.suite}, ${user.address.city}, ${user.address.zipcode}",
                style = typography.body2
            )
        }
    }
}
