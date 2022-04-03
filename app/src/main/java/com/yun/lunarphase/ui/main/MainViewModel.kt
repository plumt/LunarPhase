package com.yun.lunarphase.ui.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.yun.lunarphase.R
import com.yun.lunarphase.base.BaseViewModel
import com.yun.lunarphase.data.Constant.ADS_RESET_CNT
import com.yun.lunarphase.data.Constant.ADS_SHOW_CNT
import com.yun.lunarphase.data.Constant.SHAREDPREFERENCES_CNT_KEY
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.util.PreferenceManager

class MainViewModel (
    application: Application,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(application){

    val isLoading = MutableLiveData<Boolean>(false)

    var mInterstitialAd: InterstitialAd? = null

    init {
        settingAds()
    }

    private fun settingAds(){
        MobileAds.initialize(mContext){}
        mInterstitialAd = InterstitialAd(mContext)
        mInterstitialAd!!.adUnitId = mContext.getString(R.string.admob_front_id)
        mInterstitialAd!!.loadAd(AdRequest.Builder().build())

        mInterstitialAd!!.adListener = object : AdListener(){
            override fun onAdClosed() {
                Log.d(TAG,"광고 종료")
                mInterstitialAd!!.loadAd(AdRequest.Builder().build())
            }

            override fun onAdLoaded() {
                Log.d(TAG,"광고 준비 완료")
                if(sharedPreferences.getInt(mContext, SHAREDPREFERENCES_CNT_KEY)!! >= ADS_SHOW_CNT){
                    showAds()
                }
            }

            override fun onAdOpened() {
                sharedPreferences.setInt(mContext, SHAREDPREFERENCES_CNT_KEY, ADS_RESET_CNT)
                Log.d(TAG,"광고 오픈")
            }
        }
    }

    fun showAds(){
        if(mInterstitialAd!!.isLoaded){
            mInterstitialAd!!.show()
        } else{
            mInterstitialAd!!.loadAd(AdRequest.Builder().build())
        }
    }

}