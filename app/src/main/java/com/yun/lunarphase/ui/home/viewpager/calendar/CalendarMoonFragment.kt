package com.yun.lunarphase.ui.home.viewpager.calendar

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.yun.lunarphase.R
import com.yun.lunarphase.BR
import com.yun.lunarphase.base.BaseBindingFragment
import com.yun.lunarphase.custom.CalendarUtils
import com.yun.lunarphase.custom.CalendarUtils.Companion.getFormatString
import com.yun.lunarphase.data.Constant
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.databinding.FragmentCalendarMoonBinding
import com.yun.lunarphase.ui.home.HomeViewModel
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel

class CalendarMoonFragment
    :
    BaseBindingFragment<FragmentCalendarMoonBinding, CalendarMoonViewModel>(CalendarMoonViewModel::class.java) {
    override val viewModel: CalendarMoonViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_calendar_moon
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.calMoon
    override fun onBackEvent() {}

    val viewPagerFragment: HomeViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.isLoading.value = true

        viewPagerFragment.apply {
            moonItems.observe(viewLifecycleOwner, {
                setMoonData(screen.value!!)

            })

            screen.observe(viewLifecycleOwner, {
                setMoonData(it)

            })
        }
    }

    private fun setMoonData(screen: Int) {
        if (screen == Constant.CALENDAR_SCREEN) {
            viewModel.moonItems.value = viewPagerFragment.moonItems.value
            setCalendar()
        }
    }


    private fun setCalendar(value: Int = 0) {
        binding.cvCalendar.let {
            viewPagerFragment.run {
                Handler().postDelayed({
                    viewPagerFragment.isHomeLoading.value = false
                }, 1000)
                it.current = showDate
                it.current!!.plusMonths(value).run {
                    it.initCalendar(
                        this,
                        CalendarUtils.getMonthList(this),
                        DateTime.now(),
                        moon = viewModel.moonItems.value?: arrayListOf()
                    )
                }
            }
        }
    }
}