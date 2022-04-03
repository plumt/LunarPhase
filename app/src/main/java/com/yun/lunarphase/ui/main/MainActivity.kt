package com.yun.lunarphase.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.ads.*
import com.yun.lunarphase.R
import com.yun.lunarphase.custom.LoadingDialog
import com.yun.lunarphase.data.Constant
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.databinding.ActivityMainBinding
import com.yun.lunarphase.di.sharedPreferences
import com.yun.lunarphase.ui.home.HomeFragment
import com.yun.lunarphase.ui.popup.ExitPopup
import com.yun.lunarphase.ui.popup.WarningPopup
import com.yun.lunarphase.util.PreferenceManager
import org.joda.time.DateTime
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

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

        dialog = LoadingDialog(this)

        mainViewModel.isLoading.observe(this, {
            if (it) {
                dialog.show()
            } else {
                dialog.dismiss()
            }
        })

    }

    override fun onBackPressed() {
        showExitPopup()
    }

    private fun showExitPopup() {
        ExitPopup().apply {
            showPopup(
                this@MainActivity,
                this@MainActivity.getString(R.string.notice),
                this@MainActivity.getString(R.string.exit_question)
            )
            setDialogListener(object : ExitPopup.CustomDialogListener {
                override fun onResultClicked(result: Boolean) {
                    if (result) {
                        finish()
                    }
                }
            })
        }
    }
}
