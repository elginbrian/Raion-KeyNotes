package com.raion.keynotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.raion.keynotes.navigation.NavHost
import com.raion.keynotes.screen.NoteDAOViewModel
import com.raion.keynotes.screen.RaionAPIViewModel
import com.raion.keynotes.ui.theme.KeyNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: RaionAPIViewModel by viewModels()
                    val viewModel2: NoteDAOViewModel by viewModels()
                    KeyNotes(content = {
                        NavHost(RaionAPIViewModel = viewModel, NoteDAOViewModel = viewModel2)
                    })

                }
            }
        }
    }
}

@Composable
fun KeyNotes(content: @Composable () -> Unit){
    KeyNotesTheme {
        content()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KeyNotesTheme {

    }
}