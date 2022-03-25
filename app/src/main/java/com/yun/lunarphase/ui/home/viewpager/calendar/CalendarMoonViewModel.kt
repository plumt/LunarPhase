package com.yun.lunarphase.ui.home.viewpager.calendar

import android.app.Application
import com.yun.lunarphase.base.BaseViewModel
import com.yun.lunarphase.base.ListLiveData
import com.yun.lunarphase.data.model.MoonModel

class CalendarMoonViewModel(
    application: Application
) : BaseViewModel(application) {
    // 월령 정보
    val moonItems = ListLiveData<MoonModel.MoonModels.Datas>()
}