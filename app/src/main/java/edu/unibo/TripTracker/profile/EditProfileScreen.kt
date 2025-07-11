package edu.unibo.tracker.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.unibo.tracker.Database.User
import edu.unibo.tracker.Database.UserViewModel
import edu.unibo.tracker.commonItem.TopBarSec
import edu.unibo.tracker.login.user

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun EditProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val userState by userViewModel.getUserByEmail(user!!).collectAsState(initial = null)

    Scaffold(
        topBar = { TopBarSec("Edit Profile", navController) }
    ) {
        userState?.let {
            EditProfileContent(user = it, navController = navController, userViewModel = userViewModel)
        }
    }
}

@Composable
fun EditProfileContent(
    user: User,
    navController: NavController,
    userViewModel: UserViewModel
) {
    var sex by remember { mutableStateOf(user.sex ?: "") }
    var address by remember { mutableStateOf(user.address ?: "") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            OutlinedTextField(
                value = sex,
                onValueChange = { sex = it },
                label = { Text("Sex") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Button(
                onClick = {
                    val updatedUser = user.copy(sex = sex, address = address)
                    userViewModel.updateUser(updatedUser)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}