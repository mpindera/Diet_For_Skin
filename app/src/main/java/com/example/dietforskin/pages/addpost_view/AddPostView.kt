package com.example.dietforskin.pages.addpost_view

import androidx.compose.runtime.Composable
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.viewmodels.ProfileViewModel

@Composable
fun AddPostView(profileViewModel: ProfileViewModel) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.ADD_POST_VIEW)


}