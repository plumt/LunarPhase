package com.yun.lunarphase.di

import com.yun.lunarphase.MainViewModel
import com.yun.lunarphase.ui.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{

    viewModel {
        MainViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }
}