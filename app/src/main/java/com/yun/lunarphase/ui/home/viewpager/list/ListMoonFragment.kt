package com.yun.lunarphase.ui.home.viewpager.list

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.yun.lunarphase.R
import com.yun.lunarphase.BR
import com.yun.lunarphase.base.BaseBindingFragment
import com.yun.lunarphase.base.BaseRecyclerAdapter
import com.yun.lunarphase.custom.CalendarUtils.Companion.getFormatString
import com.yun.lunarphase.data.Constant.CALENDAR_SCREEN
import com.yun.lunarphase.data.Constant.LIST_SCREEN
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.data.model.MoonModel
import com.yun.lunarphase.databinding.FragmentListMoonBinding
import com.yun.lunarphase.databinding.ItemMoonBinding
import com.yun.lunarphase.ui.home.HomeViewModel
import org.joda.time.DateTime
import org.koin.android.viewmodel.ext.android.viewModel

class ListMoonFragment
    :
    BaseBindingFragment<FragmentListMoonBinding, ListMoonViewModel>(ListMoonViewModel::class.java) {
    override val viewModel: ListMoonViewModel by viewModel()
    override fun getResourceId(): Int = R.layout.fragment_list_moon
    override fun initData(): Boolean = true
    override fun setVariable(): Int = BR.listMoon
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

        binding.apply {
            rvMoon.run {
                adapter = object :
                    BaseRecyclerAdapter.Create<MoonModel.MoonModels.Datas, ItemMoonBinding>(
                        R.layout.item_moon,
                        bindingVariableId = BR.itemMoon,
                        bindingListener = BR.movieItemListener
                    ) {
                    override fun onItemClick(item: MoonModel.MoonModels.Datas, view: View) {

                    }
                }
            }
        }
    }

    private fun setMoonData(screen: Int) {
        if (screen == LIST_SCREEN) {
//            Handler().postDelayed({
                viewPagerFragment.isHomeLoading.value = false
//            }, 1000)

                viewPagerFragment.run {
                    viewModel.moonItems.value = moonItems.value

                    if (DateTime(nowDate).getFormatString("YYYYMM") == DateTime(showDate).getFormatString(
                            "YYYYMM"
                        )
                    ) {
                        Handler().postDelayed({
                            binding.rvMoon.smoothScrollToPosition(
                                DateTime(nowDate).getFormatString(
                                    "dd"
                                ).toInt() - 1
                            )
                        }, 10)
                    } else {
//                    binding.rvMoon.scrollToPosition(0)
                        binding.rvMoon.smoothScrollToPosition(0)
                    }
                }
//            sharedViewModel.isLoading.value = false
        }
    }
}