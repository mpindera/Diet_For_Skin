package com.example.dietforskin.pages.favorite_view

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.viewmodels.FavoritePostsViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@Composable
fun FavoritePostsView(
    favoritePostsViewModel: FavoritePostsViewModel,
    context: Context,
    profileViewModel: ProfileViewModel
) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.FAVORITE_VIEW)
    Text(text = "Fav")
}