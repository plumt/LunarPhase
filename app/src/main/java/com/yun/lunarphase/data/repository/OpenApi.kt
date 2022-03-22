package com.yun.lunarphase.data.repository

import com.yun.lunarphase.data.model.MoonModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenApi {
    @GET("/B090041/openapi/service/LunPhInfoService/getLunPhInfo")
    fun moon(
        @Query("ServiceKey") ServiceKey: String,
        @Query("solYear") solYear: String,
        @Query("solMonth") solMonth: String,
        @Query("solDay") solDay: String
    ): Observable<MoonModel.RS>
}