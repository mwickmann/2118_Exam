package com.example.pgr208_exam.database.converter

import androidx.room.TypeConverter
import java.util.Date

// dateconverter
open class DateConverter {
    @TypeConverter
    fun toDate(date: Long?): Date? {
        return date?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?) : Long?{
        return date?.time
    }
}