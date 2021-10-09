package ua.graviton.isida.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object DbTypeConverters {
    private val localFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val offsetFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter @JvmStatic
    fun toLocalDate(value: String?): LocalDate? = value?.let { localFormatter.parse(it, LocalDate::from) }

    @TypeConverter @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? = date?.format(localFormatter)

    @TypeConverter @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? = value?.let { offsetFormatter.parse(it, OffsetDateTime::from) }

    @TypeConverter @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? = date?.format(offsetFormatter)


    @TypeConverter @JvmStatic
    fun toInstant(value: Long?): Instant? = value?.let { Instant.ofEpochMilli(value) }

    @TypeConverter @JvmStatic
    fun fromInstant(date: Instant?): Long? = date?.toEpochMilli()


    @TypeConverter @JvmStatic
    fun toStringList(value: String?): List<String>? = value?.let { Json.decodeFromString<List<String>>(it) }

    @TypeConverter @JvmStatic
    fun fromStringList(array: List<String>?): String? = array?.let { Json.encodeToString(it) }


//    @TypeConverter @JvmStatic
//    fun toContentLang(value: String): ContentLang = enumValueOf(value, ContentLang.DE)

//    @TypeConverter @JvmStatic
//    fun fromContentLang(value: ContentLang) = value.name
}