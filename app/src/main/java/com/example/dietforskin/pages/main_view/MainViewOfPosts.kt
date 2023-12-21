package com.example.dietforskin.pages.main_view

import androidx.compose.runtime.Composable
import com.example.dietforskin.data.profile.PagesSite
import com.example.dietforskin.viewmodels.MainViewModel
import com.example.dietforskin.viewmodels.ProfileViewModel

@Composable
fun MainViewOfPosts(mainViewModel: MainViewModel, profileViewModel: ProfileViewModel) {
    profileViewModel.updateSelectionOfPagesSite(PagesSite.MAIN_VIEW_POSTS)

}
