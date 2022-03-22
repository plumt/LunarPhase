package com.yun.lunarphase.ui

import android.os.Bundle
import android.view.View
import com.yun.lunarphase.R
import com.yun.lunarphase.BR
import com.yun.lunarphase.base.BaseBindingFragment
import com.yun.lunarphase.base.BaseFragment
import com.yun.lunarphase.databinding.FragmentHomeBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseBindingFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class.java){
    override val viewModel: HomeViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun initData(): Boolean  = true
    override fun setVariable(): Int = BR.home
    override fun onBackEvent() { }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}