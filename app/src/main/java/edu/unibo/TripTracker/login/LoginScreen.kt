package edu.unibo.tracker.login

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.unibo.tracker.Database.UserViewModel
import kotlinx.coroutines.launch

var user : String? = null

@Composable
fun LoginScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var passwordVisualTransformation by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    val emailInteractionState = remember { MutableInteractionSource() }
    val passwordInteractionState = remember { MutableInteractionSource() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome",
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "to",
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "FitTracker",
                    style = MaterialTheme.typography.h3.copy(
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )

            }

        }
        item { Spacer(modifier = Modifier.height(18.dp)) }
        item {
            Card(

                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(300.dp),
                elevation = 6.dp,
                shape = MaterialTheme.shapes.small,


                ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.h5,
                            modifier = Modifier.padding(
                                bottom = 12.dp,
                                top = 12.dp,
                                start = 12.dp
                            )
                        )
                    }
                    OutlinedTextField(

                        value = email,
                        maxLines = 1,
                        isError = hasError,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text(text = "Username") },
                        placeholder = { Text("Username") },
                        onValueChange = {
                            email = it
                            user = email
                        },
                        interactionSource = emailInteractionState,
                    )
                    OutlinedTextField(
                        value = password,
                        maxLines = 1,
                        isError = hasError,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        label = { Text("Password") },
                        placeholder = { Text(text = "Password") },
                        onValueChange = {
                            password = it
                        },
                        interactionSource = passwordInteractionState,
                        visualTransformation = passwordVisualTransformation,
                    )
                }
            }

        }
        item {
            var loading by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    coroutineScope.launch {
                        userViewModel.getUserByEmail(email) { user ->
                            if (user != null && user.passwordHash == password) {
                                hasError = false
                                loading = true
                                navController.navigate("home")
                            } else {
                                hasError = true
                                loading = false
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                enabled = true
            )
            {
                if (loading) {
                    HorizontalDottedProgressBar()

                } else {
                    Text(
                        text = "Ready to train",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
        item {
            TextButton(onClick = { navController.navigate("registration") }) {
                Text("Don't have an account? Register")
            }
        }
    }

}
fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()