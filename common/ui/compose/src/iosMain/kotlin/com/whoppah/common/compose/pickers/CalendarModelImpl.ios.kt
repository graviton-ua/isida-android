package com.whoppah.common.compose.pickers

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.Foundation.*

/**
 * An iOS implementation of [CalendarModel] using [NSCalendar] and [NSDate].
 */
@OptIn(ExperimentalMaterial3Api::class)
internal class CalendarModelImpl(locale: CalendarLocale) : CalendarModel(locale) {

    // Used for calculating dates in UTC to match the Common/Android "utcTimeMillis" logic
    private val utcCalendar: NSCalendar = NSCalendar.currentCalendar.apply {
        timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!
    }

    // Used for Locale specific properties (First day of week, etc)
    private val localeCalendar: NSCalendar = NSCalendar.currentCalendar.apply {
        this.locale = locale
        this.timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!
    }

    override val today: CalendarDate
        get() {
            val now = NSDate()
            // Extract components in UTC
            val components = utcCalendar.components(
                unitFlags = NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
                fromDate = now
            )
            val dateAtMidnight = utcCalendar.dateFromComponents(components)!!

            return CalendarDate(
                year = components.year.toInt(),
                month = components.month.toInt(),
                dayOfMonth = components.day.toInt(),
                utcTimeMillis = (dateAtMidnight.timeIntervalSince1970 * 1000).toLong()
            )
        }

    override val firstDayOfWeek: Int
        get() {
            // NSCalendar: 1 = Sunday, 2 = Monday ... 7 = Saturday
            // ISO-8601 (Expected by abstract class): 1 = Monday ... 7 = Sunday
            val nsDay = localeCalendar.firstWeekday.toInt()
            return if (nsDay == 1) 7 else nsDay - 1
        }

    override val weekdayNames: List<Pair<String, String>>
        get() {
            val formatter = NSDateFormatter().apply { this.locale = this@CalendarModelImpl.locale }
            val fullNames = formatter.standaloneWeekdaySymbols
            val shortNames = formatter.shortStandaloneWeekdaySymbols

            val result = mutableListOf<Pair<String, String>>()

            // NS arrays start at index 0 = Sunday.
            // We need to reorder to start with Monday to match ISO-8601 expectations of the UI
            // Sunday (0) -> Last
            // Monday (1) -> First

            // Add Monday(1) through Saturday(6)
            for (i in 1 until 7) {
                result.add(fullNames[i] as String to shortNames[i] as String)
            }
            // Add Sunday(0) at the end
            result.add(fullNames[0] as String to shortNames[0] as String)

            return result
        }

    override fun getDateInputFormat(locale: CalendarLocale): DateInputFormat {
        val formatter = NSDateFormatter().apply {
            this.locale = locale
            this.dateStyle = NSDateFormatterShortStyle
            this.timeStyle = NSDateFormatterNoStyle
        }
        return datePatternAsInputFormat(formatter.dateFormat)
    }

    override fun getCanonicalDate(timeInMillis: Long): CalendarDate {
        val date = NSDate.dateWithTimeIntervalSince1970(timeInMillis / 1000.0)
        val components = utcCalendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            fromDate = date
        )
        // Re-construct to ensure 00:00:00 time
        val canonicalDate = utcCalendar.dateFromComponents(components)!!

        return CalendarDate(
            year = components.year.toInt(),
            month = components.month.toInt(),
            dayOfMonth = components.day.toInt(),
            utcTimeMillis = (canonicalDate.timeIntervalSince1970 * 1000).toLong()
        )
    }

    override fun getMonth(timeInMillis: Long): CalendarMonth {
        val date = NSDate.dateWithTimeIntervalSince1970(timeInMillis / 1000.0)
        val components = utcCalendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth,
            fromDate = date
        )
        // Set to 1st of month
        components.setDay(1)

        val firstDayDate = utcCalendar.dateFromComponents(components)!!
        return getMonthFromResolvedDate(firstDayDate, components)
    }

    override fun getMonth(date: CalendarDate): CalendarMonth {
        return getMonth(date.year, date.month)
    }

    override fun getMonth(year: Int, month: Int): CalendarMonth {
        val components = NSDateComponents().apply {
            this.year = year.toLong()
            this.month = month.toLong()
            this.day = 1
        }
        val firstDayDate = utcCalendar.dateFromComponents(components)!!
        return getMonthFromResolvedDate(firstDayDate, components)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun getMonthFromResolvedDate(firstDayDate: NSDate, components: NSDateComponents): CalendarMonth {
        val range = utcCalendar.rangeOfUnit(NSCalendarUnitDay, inUnit = NSCalendarUnitMonth, forDate = firstDayDate)
        val numberOfDays = range.useContents { length }.toInt()

        // Calculate weekday of the 1st of the month
        val weekdayComponents = utcCalendar.components(NSCalendarUnitWeekday, fromDate = firstDayDate)

        // Convert NS weekday (1=Sun...7=Sat) to ISO (1=Mon...7=Sun)
        val nsWeekday = weekdayComponents.weekday.toInt()
        val isoWeekday = if (nsWeekday == 1) 7 else nsWeekday - 1

        val difference = isoWeekday - firstDayOfWeek
        val daysFromStartOfWeek = if (difference < 0) difference + 7 else difference

        return CalendarMonth(
            year = components.year.toInt(),
            month = components.month.toInt(),
            numberOfDays = numberOfDays,
            daysFromStartOfWeekToFirstOfMonth = daysFromStartOfWeek,
            startUtcTimeMillis = (firstDayDate.timeIntervalSince1970 * 1000).toLong()
        )
    }

    override fun getDayOfWeek(date: CalendarDate): Int {
        val nsDate = NSDate.dateWithTimeIntervalSince1970(date.utcTimeMillis / 1000.0)
        val components = utcCalendar.components(NSCalendarUnitWeekday, fromDate = nsDate)
        val nsWeekday = components.weekday.toInt()
        // Convert to ISO (1=Mon...7=Sun)
        return if (nsWeekday == 1) 7 else nsWeekday - 1
    }

    override fun plusMonths(from: CalendarMonth, addedMonthsCount: Int): CalendarMonth {
        if (addedMonthsCount <= 0) return from

        val fromDate = NSDate.dateWithTimeIntervalSince1970(from.startUtcTimeMillis / 1000.0)
        val toDate = utcCalendar.dateByAddingUnit(
            unit = NSCalendarUnitMonth,
            value = addedMonthsCount.toLong(),
            toDate = fromDate,
            options = 0u
        )!!

        val components = utcCalendar.components(NSCalendarUnitYear or NSCalendarUnitMonth, fromDate = toDate)
        return getMonthFromResolvedDate(toDate, components)
    }

    override fun minusMonths(from: CalendarMonth, subtractedMonthsCount: Int): CalendarMonth {
        if (subtractedMonthsCount <= 0) return from

        val fromDate = NSDate.dateWithTimeIntervalSince1970(from.startUtcTimeMillis / 1000.0)
        val toDate = utcCalendar.dateByAddingUnit(
            unit = NSCalendarUnitMonth,
            value = -subtractedMonthsCount.toLong(),
            toDate = fromDate,
            options = 0u
        )!!

        val components = utcCalendar.components(NSCalendarUnitYear or NSCalendarUnitMonth, fromDate = toDate)
        return getMonthFromResolvedDate(toDate, components)
    }

    override fun formatWithPattern(
        utcTimeMillis: Long,
        pattern: String,
        locale: CalendarLocale
    ): String {
        val formatter = formatterCache.getOrPut("P:$pattern${locale.localeIdentifier}") {
            NSDateFormatter().apply {
                this.dateFormat = pattern
                this.locale = locale
                this.timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!
            }
        } as NSDateFormatter

        val date = NSDate.dateWithTimeIntervalSince1970(utcTimeMillis / 1000.0)
        return formatter.stringFromDate(date)
    }

    override fun parse(date: String, pattern: String, locale: CalendarLocale): CalendarDate? {
        val formatter = formatterCache.getOrPut("P:$pattern${locale.localeIdentifier}") {
            NSDateFormatter().apply {
                this.dateFormat = pattern
                this.locale = locale
                this.timeZone = NSTimeZone.timeZoneWithAbbreviation("UTC")!!
            }
        } as NSDateFormatter

        val nsDate = formatter.dateFromString(date) ?: return null

        val components = utcCalendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            fromDate = nsDate
        )

        return CalendarDate(
            year = components.year.toInt(),
            month = components.month.toInt(),
            dayOfMonth = components.day.toInt(),
            utcTimeMillis = (nsDate.timeIntervalSince1970 * 1000).toLong()
        )
    }

    override fun toString(): String {
        return "CalendarModel"
    }
}