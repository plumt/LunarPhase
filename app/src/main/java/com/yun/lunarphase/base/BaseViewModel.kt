package com.yun.lunarphase.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


open class BaseViewModel constructor(application: Application) : AndroidViewModel(application) {
    val mContext = application.applicationContext
//    val db = (application as PortfolioApplication).db

    val navigatorFlag = MutableLiveData<Int>()
}