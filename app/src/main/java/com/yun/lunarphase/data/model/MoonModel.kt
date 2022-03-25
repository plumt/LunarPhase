package com.yun.lunarphase.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.yun.lunarphase.base.Item
import com.yun.lunarphase.data.Constant


object MoonModel {
    @Xml(name = "response")
    data class RS(
        @Element
        val header: Header,
        @Element
        val body: Body
    ) {
        @Xml(name = "header")
        data class Header(
            @PropertyElement
            val resultCode: String = "",
            @PropertyElement
            val resultMsg: String = ""
        )

        @Xml(name = "body")
        data class Body(
            @Element
            val items: Items
        ) {
            @Xml(name = "items")
            data class Items(
                @Element
                val item: List<Item>?
            ) {
                @Xml(name = "item")
                data class Item(
                    @PropertyElement
                    val lunAge: String?,
                    @PropertyElement
                    val solDay: String,
                    @PropertyElement
                    val solMonth: String,
                    @PropertyElement
                    val solWeek: String,
                    @PropertyElement
                    val solYear: String
                )
            }
        }
    }
    data class MoonModels(
        val data: List<Datas>
    ){
        data class Datas(
            override val id: Int = 0,
            override val viewType: Int = 0,
            val lunAge: String?,
            val solWeek: String,
            val solDay: String,
            val solMonth: String,
            val solYear: String,
            val today: Boolean = false
        ) : Item() {
            fun lunAgeStr() = lunAge?.toFloat()?.toInt()?.toString()
        }
    }
}