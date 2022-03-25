package com.yun.lunarphase.custom

import android.content.Context
import android.graphics.*
import android.media.Image
import android.text.TextPaint
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.MotionEvent
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.core.content.withStyledAttributes
import com.yun.lunarphase.R
import com.yun.lunarphase.custom.CalendarUtils.Companion.getDateColor
import com.yun.lunarphase.custom.CalendarUtils.Companion.isSameMonth
import com.yun.lunarphase.data.Constant
import org.joda.time.DateTime


class DayItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes private val defStyleAttr: Int = R.attr.itemViewStyle,
    @StyleRes private val defStyleRes: Int = R.style.Calendar_ItemViewStyle,
    private val date: DateTime = DateTime(),
    private val firstDayOfMonth: DateTime = DateTime(),
    var current: Boolean = false,
    var select: Boolean = false,
    var event: Boolean = false,
    var moon: Int? = null,
    private val listener: (DateTime) -> Unit
) : View(ContextThemeWrapper(context, defStyleRes), attrs, defStyleAttr) {

    private val bounds = Rect()

    private var paint: Paint = Paint()

    private var selectPaint: Paint = Paint()

    private var currentPaint: Paint = Paint()

    private var eventPaint: Paint = Paint()

    init {
        /* Attributes */
        context.withStyledAttributes(attrs, R.styleable.CalendarView, defStyleAttr, defStyleRes) {
            val dayTextSize = getDimensionPixelSize(R.styleable.CalendarView_dayTextSize, 0).toFloat()

            /* 흰색 배경에 유색 글씨 */
            paint = TextPaint().apply {
                isAntiAlias = true
                textSize = dayTextSize
                color = getDateColor(date.dayOfWeek)
                if (!isSameMonth(date, firstDayOfMonth)) {
                    alpha = 50
                }
            }

            selectPaint.apply {
                style = Paint.Style.FILL
                color = Color.parseColor("#80FFD100")
            }

            currentPaint.apply {
                style = Paint.Style.STROKE
                color = Color.parseColor("#8b878d")
            }

            eventPaint.apply {
                style = Paint.Style.FILL
                color = Color.RED
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        if (isSameMonth(date, firstDayOfMonth)) {
            val half = height.toFloat() / 2
            val margin = (width - height) / 2

            if (select) {
                canvas.drawCircle(half + margin, half, half/4.7f, selectPaint)
            } else if (current) {
                canvas.drawCircle(half + margin, half, half/1.5f, currentPaint)
            }
            if(event){
                canvas.drawCircle(half + margin, half*0.5f, half/12f, eventPaint)
            }
        }

        if(moon != null) {
            val r = context.resources;
            val image = BitmapFactory.decodeResource(r, Constant.moonImgArray[moon!!])
            val resize =
                Bitmap.createScaledBitmap(image, (canvas.height / 3f).toInt(), (canvas.height / 3f).toInt(), true)
            canvas.drawBitmap(resize, ((canvas.height - canvas.width) / 4f), ((canvas.height + canvas.width) / 2.5f), null)
        }

        val date = date.dayOfMonth.toString()
        paint.getTextBounds(date, 0, date.length, bounds)

        canvas.drawText(
            date,
            (width / 2 - bounds.width() / 2).toFloat() - 2,
            (height / 2 + bounds.height() / 2).toFloat(),
            paint
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.run {
            if (action == MotionEvent.ACTION_DOWN) {
                if (isSameMonth(date, firstDayOfMonth)) {
                    listener(date)
                }
            }
        }

        return super.onTouchEvent(event)
    }
}