package com.example.illa_movies.ui.ui_component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchInput(onClick: (String) -> Unit) {
    val query = remember { mutableStateOf("") }
    Card(
        modifier = Modifier.padding(4.dp),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query.value,
            onValueChange = { query.value = it },
            maxLines = 1,
            trailingIcon = {
                IconButton(
                    onClick = { onClick(query.value) },
                ) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
                }
            }
        )
    }
}

@Preview
@Composable
fun SearchPreview() {
    SearchInput {}
}