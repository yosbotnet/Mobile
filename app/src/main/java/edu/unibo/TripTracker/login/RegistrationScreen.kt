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
import edu.unibo.tracker.Database.User
import edu.unibo.tracker.Database.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(navController: NavController, userViewModel: UserViewModel = hiltViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    var passwordVisualTransformation by remember {
        mutableStateOf<VisualTransformation>(
            PasswordVisualTransformation()
        )
    }
    val emailInteractionState = remember { MutableInteractionSource() }
    val passwordInteractionState = remember { MutableInteractionSource() }
    val confirmPasswordInteractionState = remember { MutableInteractionSource() }

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
                    text = "Join",
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
                Text(
                    text = "Start your fitness journey today",
                    style = MaterialTheme.typography.subtitle1.copy(
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )
            }
        }
        item { Spacer(modifier = Modifier.height(18.dp)) }
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .height(400.dp),
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
                            text = "Create Account",
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
                        isError = hasError && email.isBlank(),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text(text = "Username") },
                        placeholder = { Text("Enter your username") },
                        onValueChange = {
                            email = it
                            hasError = false
                        },
                        interactionSource = emailInteractionState,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = password,
                        maxLines = 1,
                        isError = hasError && password.isBlank(),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next
                        ),
                        label = { Text("Password") },
                        placeholder = { Text(text = "Create a password") },
                        onValueChange = {
                            password = it
                            hasError = false
                        },
                        interactionSource = passwordInteractionState,
                        visualTransformation = passwordVisualTransformation,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        maxLines = 1,
                        isError = hasError && (confirmPassword.isBlank() || password != confirmPassword),
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        label = { Text("Confirm Password") },
                        placeholder = { Text(text = "Confirm your password") },
                        onValueChange = {
                            confirmPassword = it
                            hasError = false
                        },
                        interactionSource = confirmPasswordInteractionState,
                        visualTransformation = passwordVisualTransformation,
                    )
                    if (hasError && errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
        item {
            var loading by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    when {
                        email.isBlank() -> {
                            hasError = true
                            errorMessage = "Username is required"
                        }
                        password.isBlank() -> {
                            hasError = true
                            errorMessage = "Password is required"
                        }
                        password != confirmPassword -> {
                            hasError = true
                            errorMessage = "Passwords don't match"
                        }
                        password.length < 6 -> {
                            hasError = true
                            errorMessage = "Password must be at least 6 characters"
                        }
                        else -> {
                            hasError = false
                            loading = true
                            coroutineScope.launch {
                                val user = User(email = email, passwordHash = password)
                                userViewModel.insert(user)
                                navController.navigate("login")
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape),
                enabled = !loading
            ) {
                if (loading) {
                    HorizontalDottedProgressBar()
                } else {
                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
        item {
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Already have an account? Sign In")
            }
        }
    }
}