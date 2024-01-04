package com.github2136.ledger.model.db

import androidx.room.TypeConverter
import com.github2136.util.JsonUtil
import com.github2136.util.date
import com.github2136.util.str
import java.util.Date

class Converters {
    private val jsonUtil by lazy { JsonUtil.instance }

    @TypeConverter
    fun fromTimestamp(value: String?) = value?.date()

    @TypeConverter
    fun fromDate(date: Date?) = date?.str()
}