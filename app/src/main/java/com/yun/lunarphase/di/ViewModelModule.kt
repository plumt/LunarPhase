package com.yun.lunarphase.di

import com.yun.lunarphase.ui.main.MainViewModel
import com.yun.lunarphase.ui.home.HomeViewModel
import com.yun.lunarphase.ui.home.viewpager.calendar.CalendarMoonViewModel
import com.yun.lunarphase.ui.home.viewpager.list.ListMoonViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        HomeViewModel(get(), get(), get())
    }

    viewModel {
        CalendarMoonViewModel(get())
    }

    viewModel {
        ListMoonViewModel(get())
    }
}