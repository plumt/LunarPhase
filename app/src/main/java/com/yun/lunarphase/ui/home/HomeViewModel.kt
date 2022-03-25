package com.yun.lunarphase.ui.home

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.yun.lunarphase.R
import com.yun.lunarphase.base.BaseViewModel
import com.yun.lunarphase.base.ListLiveData
import com.yun.lunarphase.custom.CalendarUtils.Companion.getFormatString
import com.yun.lunarphase.data.Constant.CALENDAR_SCREEN
import com.yun.lunarphase.data.Constant.LIST_SCREEN
import com.yun.lunarphase.data.Constant.NEXT_MONTH
import com.yun.lunarphase.data.Constant.PRE_MONTH
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.data.model.MoonModel
import com.yun.lunarphase.data.repository.OpenApi
import com.yun.lunarphase.util.PreferenceManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.joda.time.DateTime
import java.net.URLDecoder
import java.util.*


class HomeViewModel(
    application: Application,
    private val api: OpenApi,
    private val sharedPreferences: PreferenceManager
) : BaseViewModel(
    application
) {

    // 월령 정보
    val moonItems = ListLiveData<MoonModel.MoonModels.Datas>()

    // 화면 인덱스
    val screen = MutableLiveData(CALENDAR_SCREEN)

    // 오늘 날짜
    val nowDate: Long = DateTime().withTimeAtStartOfDay().millis

    // 날짜 변수
    var showDate = DateTime(nowDate)

    // 표시되는 날짜
    val calTitle = MutableLiveData<String>()

    // 홈 로딩
    val isHomeLoading = MutableLiveData<Boolean>(false)

    // 위상 데이터가 하나도 없을 경우
    val isNullMoon = MutableLiveData(false)

    // 광고 노출
    val openAdmob = MutableLiveData(false)

    init {
        updateMoon()
    }


    private fun callMoonData() {
        api.moon(
            URLDecoder.decode(mContext.getString(R.string.ServiceKey), "UTF-8"),
            DateTime(showDate).getFormatString("YYYY"),
            DateTime(showDate).getFormatString("MM"),
            "31"
        )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .flatMap {
                Observable.just(it)
            }.observeOn(AndroidSchedulers.mainThread()).map {
                moonItems.value = addItem(it)
                if (it.body.items.item == null) {
                    isNullMoon.value = true
                }
            }.subscribe({
                isHomeLoading.value = false
            }, {
                Log.e(TAG, "error : ${it.message}")
                isHomeLoading.value = false
                isNullMoon.value = true
            }, {
                cntCheck()
            })
    }


    private fun addItem(moonModel: MoonModel.RS): ArrayList<MoonModel.MoonModels.Datas>? {
        if (moonModel.body.items.item?.size ?: -1 > 0) {
            val temp = arrayListOf<MoonModel.MoonModels.Datas>()
            moonModel.body.items.item?.forEach { item ->
                item.run {
                    temp.add(
                        MoonModel.MoonModels.Datas(
                            temp.size, 0, lunAge, solWeek, solDay, solMonth, solYear,
                            checkTotay(solYear, solMonth, solDay)
                        )
                    )
                }
            }
            return temp
        } else {
            return null
        }
    }

    private fun checkTotay(year: String, month: String, day: String): Boolean =
        DateTime(nowDate).getFormatString("YYYY") == year &&
                DateTime(nowDate).getFormatString("MM") == month &&
                DateTime(nowDate).getFormatString("dd") == day


    fun updateMoon(recovery: Boolean = false) {
        calTitle.value = DateTime(showDate).getFormatString("YYYY년 MM월")
        if(recovery){
            moonItems.clear(true)
        } else {
            callMoonData()
        }
        isHomeLoading.value = true
    }

    private fun cntCheck(){
        if(sharedPreferences.getInt(mContext, "cnt")!! >= 4){
            Toast.makeText(mContext, "광고 노출!", Toast.LENGTH_SHORT).show()
            openAdmob.value = true
            sharedPreferences.setInt(mContext, "cnt", 0)
        } else{
            sharedPreferences.setInt(
                mContext,
                "cnt",
                sharedPreferences.getInt(mContext, "cnt")!! + 1
            )
        }
        Log.d(TAG, "cnt : ${sharedPreferences.getInt(mContext, "cnt")}")

    }

    fun onClick(v: View) {
        isHomeLoading.value = true
        when (v.tag.toString()) {
            CALENDAR_SCREEN.toString(), LIST_SCREEN.toString() -> screen.value =
                v.tag.toString().toInt()
            NEXT_MONTH.toString() -> {
                showDate = DateTime(showDate).plusMonths(1)
                updateMoon()
            }
            PRE_MONTH.toString() -> {
                showDate = DateTime(showDate).minusMonths(1)
                updateMoon()
            }
        }
    }
}