package com.yun.lunarphase.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.button.MaterialButton
import com.yun.lunarphase.R
import com.yun.lunarphase.BR
import com.yun.lunarphase.base.BaseBindingFragment
import com.yun.lunarphase.custom.ZoomOutPageTransformer
import com.yun.lunarphase.data.Constant
import com.yun.lunarphase.data.Constant.CALENDAR_SCREEN
import com.yun.lunarphase.data.Constant.LIST_SCREEN
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.databinding.FragmentHomeBinding
import com.yun.lunarphase.ui.home.viewpager.calendar.CalendarMoonFragment
import com.yun.lunarphase.ui.home.viewpager.list.ListMoonFragment
import com.yun.lunarphase.ui.popup.WarningPopup
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseBindingFragment<FragmentHomeBinding, HomeViewModel>(HomeViewModel::class.java) {
    override val viewModel: HomeViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_home
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.home
    override fun onBackEvent() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            vpMoon.run {
//                setPageTransformer(ZoomOutPageTransformer())
                isUserInputEnabled = false
                adapter = object : FragmentStateAdapter(this@HomeFragment) {
                    override fun getItemCount(): Int = 2
                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> CalendarMoonFragment()
                            1 -> ListMoonFragment()
                            else -> Fragment()
                        }
                    }
                }
            }

            viewModel.screen.observe(viewLifecycleOwner, {
                if (it == LIST_SCREEN || it == CALENDAR_SCREEN) {
                    vpMoon.setCurrentItem(it, true)
                }
            })

            viewModel.isHomeLoading.observe(viewLifecycleOwner,{
                sharedViewModel.isLoading.value = it
            })

            viewModel.isNullMoon.observe(viewLifecycleOwner,{
                if(it){
                    viewModel.isNullMoon.value = false
                    showPopup()
                }
            })

            viewModel.openAdmob.observe(viewLifecycleOwner,{
                if(it){
                    viewModel.openAdmob.value = false
                    sharedViewModel.mInterstitialAd.show()
                }
            })
        }
    }

    private fun showPopup(){
        WarningPopup().apply {
            showPopup(requireContext(),"알림","달의 위상 정보를 가져올 수 없습니다.")
            setDialogListener(object : WarningPopup.CustomDialogListener{
                override fun onResultClicked(result: Boolean) {
                    viewModel.run {
//                        showDate = DateTime(nowDate)
                        updateMoon(true)
                    }
                }
            })
        }
    }
}