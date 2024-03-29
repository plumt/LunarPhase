package com.yun.lunarphase.custom

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import androidx.core.view.children
import com.yun.lunarphase.R
import com.yun.lunarphase.custom.CalendarUtils.Companion.WEEKS_PER_MONTH
import com.yun.lunarphase.custom.CalendarUtils.Companion.getFormatString
import com.yun.lunarphase.custom.CalendarUtils.Companion.isSameDay
import com.yun.lunarphase.data.model.MoonModel
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import kotlin.math.max


class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.calendarViewStyle,
    @StyleRes defStyleRes: Int = R.style.Calendar_CalendarViewStyle,
) : ViewGroup(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private var _height: Float = 0f

    var listener: OnCalendarListener? = null

    var current: DateTime? = null

    interface OnCalendarListener {
        fun onClick(dateTime: DateTime)
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            _height = getDimension(R.styleable.CalendarView_dayHeight, 0f)
        }
    }

    /**
     * Measure
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val h = paddingTop + paddingBottom + max(
            suggestedMinimumHeight,
            (_height * WEEKS_PER_MONTH).toInt()
        )
        setMeasuredDimension(getDefaultSize(suggestedMinimumWidth, widthMeasureSpec), h)
    }

    /**
     * Layout
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val iWidth = (width / DateTimeConstants.DAYS_PER_WEEK).toFloat()
        val iHeight = (height / WEEKS_PER_MONTH).toFloat()

        var index = 0
        children.forEach { view ->
            val left = (index % DateTimeConstants.DAYS_PER_WEEK) * iWidth
            val top = (index / DateTimeConstants.DAYS_PER_WEEK) * iHeight

            view.layout(left.toInt(), top.toInt(), (left + iWidth).toInt(), (top + iHeight).toInt())

            index++
        }
    }

    /**
     * 달력 그리기 시작한다.
     * @param firstDayOfMonth   한 달의 시작 요일
     * @param list              달력이 가지고 있는 요일과 이벤트 목록 (총 42개)
     */
    fun initCalendar(
        firstDayOfMonth: DateTime,
        list: List<DateTime>,
        selectDayOfMonth: DateTime? = null,
        eventDate: List<DateTime>? = null,
        moon: List<MoonModel.MoonModels.Datas>? = null
    ) {
        current = firstDayOfMonth
        removeAllViews()
        var index = 0
        list.forEachIndexed { i, it ->
            addView(DayItemView(
                context = context,
                date = it,
                firstDayOfMonth = firstDayOfMonth,
                select = isSameDay(it, selectDayOfMonth),
                current = isSameDay(it, DateTime.now()),
                event = if (index < eventDate?.size ?: -1) {
                    isSameDay(it, eventDate!![index])
                } else {
                    false
                },
                moon = if(moon?.size?:-1 > index){
                    if(moon?.get(index)?.solDay?:"" == DateTime(it).getFormatString("dd")) {
                        moon?.get(index)?.lunAge?.toFloat()?.toInt()
                    } else null

                } else{
                    null
                }
            ) {
                listener?.onClick(it)
            })
            if (index < moon?.size?: -1 && moon?.get(index)?.solDay?:"" == DateTime(it).getFormatString("dd")) {
                index++
            }
        }
        invalidate()
    }
}