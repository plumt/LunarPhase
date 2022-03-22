package com.yun.lunarphase

import android.app.Application
import com.yun.lunarphase.di.apiModule
import com.yun.lunarphase.di.networkModule
import com.yun.lunarphase.di.sharedPreferences
import com.yun.lunarphase.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class LunarPhaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            val androidContext = androidContext(this@LunarPhaseApplication)
            koin.loadModules(
                listOf(
                    viewModelModule,
                    sharedPreferences,
                    networkModule,
                    apiModule
                )
            )
            koin.createRootScope()
        }
    }
}