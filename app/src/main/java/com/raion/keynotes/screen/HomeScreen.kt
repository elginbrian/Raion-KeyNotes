package com.raion.keynotes.screen

import androidx.compose.runtime.Composable
import com.raion.keynotes.component.Notes

@Composable
fun HomeScreen(viewModel: RaionViewModel){
    Notes(viewModel = viewModel)
}