package com.example.illa_movies.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.illa_movies.ui.theme.ILLAMoviesTheme

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ILLAMoviesTheme{
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "HomeFragment",
                    )
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}