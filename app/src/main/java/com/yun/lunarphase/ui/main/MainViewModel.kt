package com.yun.lunarphase.ui.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.yun.lunarphase.R
import com.yun.lunarphase.base.BaseViewModel

class MainViewModel (
    application: Application
) : BaseViewModel(application){

    val isLoading = MutableLiveData<Boolean>(false)

    val mInterstitialAd = InterstitialAd(mContext)


    init{
        mInterstitialAd.adUnitId = mContext.getString(R.string.admob_front_test_id)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        mInterstitialAd.adListener = object : AdListener(){
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }
    }

}