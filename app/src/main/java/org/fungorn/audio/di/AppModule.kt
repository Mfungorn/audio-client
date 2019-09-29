package org.fungorn.audio.di

import org.fungorn.audio.ui.dashboard.DashboardViewModel
import org.fungorn.audio.ui.home.HomeViewModel
import org.fungorn.audio.ui.notifications.NotificationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private var appModule = module {
    viewModel { DashboardViewModel() }
    viewModel { HomeViewModel() }
    viewModel { NotificationsViewModel() }
}

val audioApp = listOf(appModule)