package com.yun.lunarphase.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.yun.lunarphase.R
import com.yun.lunarphase.custom.LoadingDialog
import com.yun.lunarphase.data.Constant
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.databinding.ActivityMainBinding
import com.yun.lunarphase.di.sharedPreferences
import com.yun.lunarphase.ui.popup.WarningPopup
import com.yun.lunarphase.util.PreferenceManager
import org.joda.time.DateTime
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    val sharedPreferences: PreferenceManager by inject()

    lateinit var dialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.main = mainViewModel

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = applicationContext.getString(R.string.admob_banner_test_id)

        dialog = LoadingDialog(this)

        mainViewModel.isLoading.observe(this,{
            if(it){
                dialog.show()
            } else{
                dialog.dismiss()
            }
        })
    }
}