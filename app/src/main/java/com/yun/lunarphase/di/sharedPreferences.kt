package com.yun.lunarphase.di

import com.yun.lunarphase.util.PreferenceManager
import org.koin.dsl.module

val sharedPreferences = module {
    fun provideSharedPref(): PreferenceManager {
        return PreferenceManager
    }
    single { provideSharedPref() }
}