package com.yun.lunarphase.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yun.lunarphase.R
import com.yun.lunarphase.data.Constant
import com.yun.lunarphase.data.Constant.TAG
import com.yun.lunarphase.data.Constant.moonImgArray

object PreferenceManager {
    const val PREFERENCES_NAME = "portpolio"
    const val DEFAULT_VALUE_STRING = ""
    private const val DEFAULT_VALUE_BOOLEAN = false
    private const val DEFAULT_VALUE_INT = -1
    private const val DEFAULT_VALUE_LONG = -1L
    private const val DEFAULT_VALUE_FLOAT = -1f
    open fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    fun setString(context: Context, key: String?, value: String?) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun setInt(context: Context, key: String?, value: Int) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putInt(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String?): String? {
        val prefs = getPreferences(context)
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    fun getInt(context: Context, key: String?): Int? {
        val prefs = getPreferences(context)
        return prefs.getInt(key, DEFAULT_VALUE_INT)
    }

    fun getAll(context: Context): MutableCollection<out Any?> {
        val prefs = getPreferences(context)
        return prefs.all.keys
    }
}

object Util {
    @BindingAdapter("setImages")
    @JvmStatic
    fun ImageView.setImages(path: String?) {
        this.run {
            Glide.with(context)
                .load(moonImgArray[path!!.toInt()])
                .into(this)
        }
    }
}
