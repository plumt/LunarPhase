package com.yun.lunarphase.di

import com.yun.lunarphase.data.repository.OpenApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {

    fun providerOpenApi(retrofit: Retrofit): OpenApi {
        return retrofit.create(OpenApi::class.java)
    }

    single() { providerOpenApi(get()) }
}