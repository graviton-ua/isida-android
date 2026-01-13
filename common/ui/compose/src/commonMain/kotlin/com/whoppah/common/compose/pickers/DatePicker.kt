package com.whoppah.common.compose.pickers

import androidx.compose.animation.*
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapLayoutInfoProvider
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastJoinToString
import com.whoppah.common.compose.icons.WhIcons
import com.whoppah.common.compose.icons.navigation.KeyboardArrowLeft
import com.whoppah.common.compose.icons.navigation.KeyboardArrowRight
import com.whoppah.common.compose.icons.navigation.MenuChevronDown
import com.whoppah.common.compose.pickers.DatePickerStateImpl.Companion.Saver
import com.whoppah.common.compose.theme.WhoppahPalette
import com.whoppah.common.compose.theme.WhoppahTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.max

@Composable
fun DatePicker(
    selectedDatesMillis: List<Long>,
    onDateSelectionChange: (Long) -> Unit,
    state: DatePickerState,
    modifier: Modifier = Modifier,
    dateFormatter: DatePickerFormatter = remember { DatePickerDefaults.dateFormatter() },
    headline: (@Composable () -> Unit)? = {
        DatePickerDefaults.DatePickerHeadline(
            selectedDatesMillis = selectedDatesMillis,
            dateFormatter = dateFormatter,
            modifier = Modifier.padding(DatePickerHeadlinePadding)
        )
    },
    colors: DatePickerColors = DatePickerDefaults.colors(),
    yearPickerEnabled: Boolean = false,
    showEmptyWeek: Boolean = false,
) {
    val defaultLocale = defaultLocale()
    val calendarModel = remember(defaultLocale) { createCalendarModel(defaultLocale) }
    DateEntryContainer(
        modifier = modifier,
        headline = headline,
        headlineTextStyle = WhoppahTheme.typography.h4,
        colors = colors,
    ) {
        DatePickerContent(
            selectedDatesMillis = selectedDatesMillis,
            displayedMonthMillis = state.displayedMonthMillis,
            onDateSelectionChange = onDateSelectionChange,
            onDisplayedMonthChange = { monthInMillis -> state.displayedMonthMillis = monthInMillis },
            calendarModel = calendarModel,
            yearRange = state.yearRange,
            yearPickerEnabled = yearPickerEnabled,
            dateFormatter = dateFormatter,
            selectableDates = state.selectableDates,
            colors = colors,
            showEmptyWeek = showEmptyWeek,
        )
    }
}

/**
 * A state object that can be hoisted to observe the date picker state. See
 * [rememberDatePickerState].
 */
@Stable
interface DatePickerState {
    /**
     * A timestamp that represents the currently displayed month _start_ date in _UTC_ milliseconds
     * from the epoch.
     *
     * @throws IllegalArgumentException in case the value is set with a timestamp that does not fall
     * within the [yearRange].
     */
    var displayedMonthMillis: Long

    /**
     * An [IntRange] that holds the year range that the date picker will be limited to.
     */
    val yearRange: IntRange

    var selectableDates: SelectableDates
}

/**
 * An interface that controls the selectable dates and years in the date pickers UI.
 */

@Stable
interface SelectableDates {
    /**
     * Returns true if the date item representing the [utcTimeMillis] should be enabled for
     * selection in the UI.
     */
    fun isSelectableDate(utcTimeMillis: Long) = true

    /**
     * Returns true if a given [year] should be enabled for selection in the UI. When a year is
     * defined as non selectable, all the dates in that year will also be non selectable.
     */
    fun isSelectableYear(year: Int) = true
}

/**
 * A date formatter interface used by [DatePicker].
 */
interface DatePickerFormatter {
    /**
     * Format a given [monthMillis] to a string representation of the month and the year (i.e.
     * January 2023).
     *
     * @param monthMillis timestamp in _UTC_ milliseconds from the epoch that represents the month
     * @param locale a [CalendarLocale] to use when formatting the month and year
     */
    fun formatMonthYear(@Suppress("AutoBoxing") monthMillis: Long?, locale: CalendarLocale): String?

    /**
     * Format a given [dateMillis] to a string representation of the date (i.e. Mar 27, 2021).
     *
     * @param dateMillis timestamp in _UTC_ milliseconds from the epoch that represents the date
     * @param locale a [CalendarLocale] to use when formatting the date
     * @param forContentDescription indicates that the requested formatting is for content
     *   description. In these cases, the output may include a more descriptive wording that will be
     *   passed to a screen readers.
     */
    fun formatDate(
        @Suppress("AutoBoxing") dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean = false,
    ): String?
}

/**
 * Creates a [DatePickerState] for a [DatePicker] that is remembered across compositions.
 *
 * To create a date picker state outside composition, see the `DatePickerState` function.
 *
 * @param initialSelectedDateMillis timestamp in _UTC_ milliseconds from the epoch that represents
 * an initial selection of a date. Provide a `null` to indicate no selection.
 * @param initialDisplayedMonthMillis timestamp in _UTC_ milliseconds from the epoch that represents
 * an initial selection of a month to be displayed to the user. By default, in case an
 * `initialSelectedDateMillis` is provided, the initial displayed month would be the month of the
 * selected date. Otherwise, in case `null` is provided, the displayed month would be the
 * current one.
 * @param yearRange an [IntRange] that holds the year range that the date picker will be limited to
 * @param initialDisplayMode an initial [DisplayMode] that this state will hold
 * @param selectableDates a [SelectableDates] that is consulted to check if a date is allowed. In
 * case a date is not allowed to be selected, it will appear disabled in the UI.
 */
@Composable
fun rememberDatePickerState(
    initialDisplayedMonthMillis: Long? = null,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
): DatePickerState {
    val locale = defaultLocale()
    return rememberSaveable(
        selectableDates,
        saver = DatePickerStateImpl.Saver(selectableDates, locale)
    ) {
        DatePickerStateImpl(
            initialDisplayedMonthMillis = initialDisplayedMonthMillis,
            yearRange = yearRange,
            selectableDates = selectableDates,
            locale = locale
        )
    }
}

/**
 * Creates a [DatePickerState].
 *
 * For most cases, you are advised to use the [rememberDatePickerState] when in a composition.
 *
 * Note that in case you provide a [locale] that is different than the default platform locale, you
 * may need to ensure that the picker's title and headline are localized correctly. The following
 * sample shows one possible way of doing so by applying a local composition of a `LocalContext` and
 * `LocaleConfiguration`.
 *
 * @sample androidx.compose.material3.samples.DatePickerCustomLocaleSample
 * @param locale the [CalendarLocale] that will be used when formatting dates, determining the input
 *   format, displaying the week-day, determining the first day of the week, and more. Note that in
 *   case the provided [CalendarLocale] differs from the platform's default Locale, you may need to
 *   ensure that the picker's title and headline are localized correctly, and in some cases, you may
 *   need to apply an RTL layout.
 * @param initialSelectedDateMillis timestamp in _UTC_ milliseconds from the epoch that represents
 *   an initial selection of a date. Provide a `null` to indicate no selection. Note that the
 *   state's [DatePickerState.selectedDateMillis] will provide a timestamp that represents the
 *   _start_ of the day, which may be different than the provided initialSelectedDateMillis.
 * @param initialDisplayedMonthMillis timestamp in _UTC_ milliseconds from the epoch that represents
 *   an initial selection of a month to be displayed to the user. In case `null` is provided, the
 *   displayed month would be the current one.
 * @param yearRange an [IntRange] that holds the year range that the date picker will be limited to
 * @param initialDisplayMode an initial [DisplayMode] that this state will hold
 * @param selectableDates a [SelectableDates] that is consulted to check if a date is allowed. In
 *   case a date is not allowed to be selected, it will appear disabled in the UI.
 * @throws [IllegalArgumentException] if the initial selected date or displayed month represent a
 *   year that is out of the year range.
 * @see rememberDatePickerState
 */
fun DatePickerState(
    locale: CalendarLocale,
    initialDisplayedMonthMillis: Long? = null,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
): DatePickerState = DatePickerStateImpl(
    initialDisplayedMonthMillis = initialDisplayedMonthMillis,
    yearRange = yearRange,
    selectableDates = selectableDates,
    locale = locale
)

/**
 * Contains default values used by the [DatePicker].
 */
@Stable
object DatePickerDefaults {
    /**
     * Creates a [DatePickerColors] that will potentially animate between the provided colors
     * according to the Material specification.
     *
     * @param containerColor the color used for the date picker's background
     * @param titleContentColor the color used for the date picker's title
     * @param headlineContentColor the color used for the date picker's headline
     * @param weekdayContentColor the color used for the weekday letters
     * @param subheadContentColor the color used for the month and year subhead labels that appear
     * when months are displayed at a `DateRangePicker`.
     * @param navigationContentColor the content color used for the year selection menu button and
     * the months arrow navigation when displayed at a `DatePicker`.
     * @param yearContentColor the color used for a year item content
     * @param disabledYearContentColor the color used for a disabled year item content
     * @param currentYearContentColor the color used for the current year content when selecting a
     * year
     * @param selectedYearContentColor the color used for a selected year item content
     * @param disabledSelectedYearContentColor the color used for a disabled selected year item
     * content
     * @param selectedYearContainerColor the color used for a selected year item container
     * @param disabledSelectedYearContainerColor the color used for a disabled selected year item
     * container
     * @param dayContentColor the color used for days content
     * @param disabledDayContentColor the color used for disabled days content
     * @param selectedDayContentColor the color used for selected days content
     * @param disabledSelectedDayContentColor the color used for disabled selected days content
     * @param selectedDayContainerColor the color used for a selected day container
     * @param disabledSelectedDayContainerColor the color used for a disabled selected day container
     * @param todayContentColor the color used for the day that marks the current date
     * @param todayDateBorderColor the color used for the border of the day that marks the current
     * date
     * @param dayInSelectionRangeContentColor the content color used for days that are within a date
     * range selection
     * @param dayInSelectionRangeContainerColor the container color used for days that are within a
     * date range selection
     * @param dividerColor the color used for the dividers used at the date pickers
     * @param dateTextFieldColors the [TextFieldColors] defaults for the date text field when in
     * [DisplayMode.Input]. See [OutlinedTextFieldDefaults.colors].
     */
    @Composable
    fun colors(
        containerColor: Color = WhoppahTheme.colors.background,
        titleContentColor: Color = contentColorFor(backgroundColor = containerColor),
        headlineContentColor: Color = contentColorFor(backgroundColor = containerColor),
        weekdayContentColor: Color = WhoppahPalette.Grey500,
        subheadContentColor: Color = contentColorFor(backgroundColor = containerColor),
        navigationContentColor: Color = contentColorFor(backgroundColor = containerColor),

        yearContentColor: Color = contentColorFor(backgroundColor = containerColor),
        disabledYearContentColor: Color = WhoppahPalette.Grey500,
        currentYearContentColor: Color = WhoppahTheme.colors.primary,
        selectedYearContentColor: Color = yearContentColor,
        disabledSelectedYearContentColor: Color = disabledYearContentColor,
        selectedYearContainerColor: Color = WhoppahPalette.Curious50,
        disabledSelectedYearContainerColor: Color = WhoppahPalette.Grey50,

        dayContentColor: Color = contentColorFor(backgroundColor = containerColor),
        disabledDayContentColor: Color = WhoppahPalette.Grey500,
        selectedDayContentColor: Color = dayContentColor,
        disabledSelectedDayContentColor: Color = disabledDayContentColor,
        selectedDayContainerColor: Color = WhoppahPalette.Curious50,
        disabledSelectedDayContainerColor: Color = WhoppahPalette.Grey50,

        todayContentColor: Color = WhoppahTheme.colors.primary,
        todayDateBorderColor: Color = navigationContentColor,
        dayInSelectionRangeContentColor: Color = LocalContentColor.current,
        dayInSelectionRangeContainerColor: Color = LocalContentColor.current,
        dividerColor: Color = LocalContentColor.current,
    ): DatePickerColors = DatePickerColors(
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        headlineContentColor = headlineContentColor,
        weekdayContentColor = weekdayContentColor,
        subheadContentColor = subheadContentColor,
        navigationContentColor = navigationContentColor,
        yearContentColor = yearContentColor,
        disabledYearContentColor = disabledYearContentColor,
        currentYearContentColor = currentYearContentColor,
        selectedYearContentColor = selectedYearContentColor,
        disabledSelectedYearContentColor = disabledSelectedYearContentColor,
        selectedYearContainerColor = selectedYearContainerColor,
        disabledSelectedYearContainerColor = disabledSelectedYearContainerColor,
        dayContentColor = dayContentColor,
        disabledDayContentColor = disabledDayContentColor,
        selectedDayContentColor = selectedDayContentColor,
        disabledSelectedDayContentColor = disabledSelectedDayContentColor,
        selectedDayContainerColor = selectedDayContainerColor,
        disabledSelectedDayContainerColor = disabledSelectedDayContainerColor,
        todayContentColor = todayContentColor,
        todayDateBorderColor = todayDateBorderColor,
        dayInSelectionRangeContentColor = dayInSelectionRangeContentColor,
        dayInSelectionRangeContainerColor = dayInSelectionRangeContainerColor,
        dividerColor = dividerColor,
    )


    /**
     * Returns a [DatePickerFormatter].
     *
     * The date formatter will apply the best possible localized form of the given skeleton and Locale.
     * A skeleton is similar to, and uses the same format characters as, a Unicode
     * <a href="http://www.unicode.org/reports/tr35/#Date_Format_Patterns">UTS #35</a> pattern.
     *
     * One difference is that order is irrelevant. For example, "MMMMd" will return "MMMM d" in the
     * `en_US` locale, but "d. MMMM" in the `de_CH` locale.
     *
     * @param yearSelectionSkeleton a date format skeleton used to format the date picker's year
     * selection menu button (e.g. "March 2021").
     * @param selectedDateSkeleton a date format skeleton used to format a selected date (e.g.
     * "Mar 27, 2021")
     * @param selectedDateDescriptionSkeleton a date format skeleton used to format a selected date to
     * be used as content description for screen readers (e.g. "Saturday, March 27, 2021")
     */
    fun dateFormatter(
        yearSelectionSkeleton: String = YearMonthSkeleton,
        selectedDateSkeleton: String = YearAbbrMonthDaySkeleton,
        selectedDateDescriptionSkeleton: String = YearMonthWeekdayDaySkeleton
    ): DatePickerFormatter = DatePickerFormatterImpl(
        yearSelectionSkeleton = yearSelectionSkeleton,
        selectedDateSkeleton = selectedDateSkeleton,
        selectedDateDescriptionSkeleton = selectedDateDescriptionSkeleton
    )

    /**
     * A default date picker headline composable that displays a default headline text when there is
     * no date selection, and an actual date string when there is.
     *
     * @param selectedDatesMillis a timestamp that represents the selected date _start_ of the day in
     * _UTC_ milliseconds from the epoch
     * @param displayMode the current [DisplayMode]
     * @param dateFormatter a [DatePickerFormatter]
     * @param modifier a [Modifier] to be applied for the headline
     */
    @Composable
    fun DatePickerHeadline(
        selectedDatesMillis: List<Long>,
        dateFormatter: DatePickerFormatter,
        modifier: Modifier = Modifier
    ) {
        val defaultLocale = defaultLocale()
        val formattedDates = selectedDatesMillis.map { dateFormatter.formatDate(dateMillis = it, locale = defaultLocale) }
            .let { if (it.isNotEmpty()) it.fastJoinToString(",") else null }
        val verboseDateDescription = selectedDatesMillis.lastOrNull()?.let {
            dateFormatter.formatDate(
                dateMillis = it,
                locale = defaultLocale,
                forContentDescription = true
            )
        } ?: "date_picker_no_selection_description"
        val headlineText = formattedDates ?: "date_picker_headline"
        val headlineDescription = "date_picker_headline_description $verboseDateDescription"
        Text(
            text = headlineText,
            modifier = modifier.semantics {
                liveRegion = LiveRegionMode.Polite
                contentDescription = headlineDescription
            },
            maxLines = 1
        )
    }

    /**
     * Creates and remembers a [FlingBehavior] that will represent natural fling curve with snap to
     * the most visible month in the months list.
     *
     * @param lazyListState a [LazyListState]
     * @param decayAnimationSpec the decay to use
     */
    @Composable
    internal fun rememberSnapFlingBehavior(
        lazyListState: LazyListState,
        decayAnimationSpec: DecayAnimationSpec<Float> = exponentialDecay()
    ): FlingBehavior {
        val rr: SnapLayoutInfoProvider = remember(decayAnimationSpec, lazyListState) {
            val original = SnapLayoutInfoProvider(lazyListState)
            object : SnapLayoutInfoProvider by original {
                override fun calculateApproachOffset(velocity: Float, decayOffset: Float): Float = 0.0f
            }
        }
        return androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior(snapLayoutInfoProvider = rr)
    }

    /** The range of years for the date picker dialogs. */
    val YearRange: IntRange = IntRange(2024, 2070)

    /** The default shape for date picker dialogs. */
    val shape: Shape @Composable get() = WhoppahTheme.shapes.large

    /**
     * A default [SelectableDates] that allows all dates to be selected.
     */
    val AllDates: SelectableDates = object : SelectableDates {}

    /**
     * A date format skeleton used to format the date picker's year selection menu button (e.g.
     * "March 2021")
     */
    const val YearMonthSkeleton: String = "yMMMM"

    /**
     * A date format skeleton used to format a selected date (e.g. "Mar 27, 2021")
     */
    const val YearAbbrMonthDaySkeleton: String = "yMMMd"

    /**
     * A date format skeleton used to format a selected date to be used as content description for
     * screen readers (e.g. "Saturday, March 27, 2021")
     */
    const val YearMonthWeekdayDaySkeleton: String = "yMMMMEEEEd"
}

/**
 * Represents the colors used by the date picker.
 *
 * @constructor create an instance with arbitrary colors, see [DatePickerDefaults.colors] for the
 * default implementation that follows Material specifications.
 *
 * @param containerColor the color used for the date picker's background
 * @param titleContentColor the color used for the date picker's title
 * @param headlineContentColor the color used for the date picker's headline
 * @param weekdayContentColor the color used for the weekday letters
 * @param subheadContentColor the color used for the month and year subhead labels that appear
 * when months are displayed at a `DateRangePicker`.
 * @param navigationContentColor the content color used for the year selection menu button and
 * the months arrow navigation when displayed at a `DatePicker`.
 * @param yearContentColor the color used for a year item content
 * @param disabledYearContentColor the color used for a disabled year item content
 * @param currentYearContentColor the color used for the current year content when selecting a
 * year
 * @param selectedYearContentColor the color used for a selected year item content
 * @param disabledSelectedYearContentColor the color used for a disabled selected year item
 * content
 * @param selectedYearContainerColor the color used for a selected year item container
 * @param disabledSelectedYearContainerColor the color used for a disabled selected year item
 * container
 * @param dayContentColor the color used for days content
 * @param disabledDayContentColor the color used for disabled days content
 * @param selectedDayContentColor the color used for selected days content
 * @param disabledSelectedDayContentColor the color used for disabled selected days content
 * @param selectedDayContainerColor the color used for a selected day container
 * @param disabledSelectedDayContainerColor the color used for a disabled selected day container
 * @param todayContentColor the color used for the day that marks the current date
 * @param todayDateBorderColor the color used for the border of the day that marks the current
 * date
 * @param dayInSelectionRangeContentColor the content color used for days that are within a date
 * range selection
 * @param dayInSelectionRangeContainerColor the container color used for days that are within a
 * date range selection
 * @param dividerColor the color used for the dividers used at the date pickers
 */
@Immutable
class DatePickerColors(
    val containerColor: Color,
    val titleContentColor: Color,
    val headlineContentColor: Color,
    val weekdayContentColor: Color,
    val subheadContentColor: Color,
    val navigationContentColor: Color,
    val yearContentColor: Color,
    val disabledYearContentColor: Color,
    val currentYearContentColor: Color,
    val selectedYearContentColor: Color,
    val disabledSelectedYearContentColor: Color,
    val selectedYearContainerColor: Color,
    val disabledSelectedYearContainerColor: Color,
    val dayContentColor: Color,
    val disabledDayContentColor: Color,
    val selectedDayContentColor: Color,
    val disabledSelectedDayContentColor: Color,
    val selectedDayContainerColor: Color,
    val disabledSelectedDayContainerColor: Color,
    val todayContentColor: Color,
    val todayDateBorderColor: Color,
    val dayInSelectionRangeContainerColor: Color,
    val dayInSelectionRangeContentColor: Color,
    val dividerColor: Color,
) {
    /**
     * Returns a copy of this DatePickerColors, optionally overriding some of the values.
     * This uses the Color.Unspecified to mean “use the value from the source”
     * // For `dateTextFieldColors` use null to mean "use the value from source"
     */
    fun copy(
        containerColor: Color = this.containerColor,
        titleContentColor: Color = this.titleContentColor,
        headlineContentColor: Color = this.headlineContentColor,
        weekdayContentColor: Color = this.weekdayContentColor,
        subheadContentColor: Color = this.subheadContentColor,
        navigationContentColor: Color = this.navigationContentColor,
        yearContentColor: Color = this.yearContentColor,
        disabledYearContentColor: Color = this.disabledYearContentColor,
        currentYearContentColor: Color = this.currentYearContentColor,
        selectedYearContentColor: Color = this.selectedYearContentColor,
        disabledSelectedYearContentColor: Color = this.disabledSelectedYearContentColor,
        selectedYearContainerColor: Color = this.selectedYearContainerColor,
        disabledSelectedYearContainerColor: Color = this.disabledSelectedYearContainerColor,
        dayContentColor: Color = this.dayContentColor,
        disabledDayContentColor: Color = this.disabledDayContentColor,
        selectedDayContentColor: Color = this.selectedDayContentColor,
        disabledSelectedDayContentColor: Color = this.disabledSelectedDayContentColor,
        selectedDayContainerColor: Color = this.selectedDayContainerColor,
        disabledSelectedDayContainerColor: Color = this.disabledSelectedDayContainerColor,
        todayContentColor: Color = this.todayContentColor,
        todayDateBorderColor: Color = this.todayDateBorderColor,
        dayInSelectionRangeContainerColor: Color = this.dayInSelectionRangeContainerColor,
        dayInSelectionRangeContentColor: Color = this.dayInSelectionRangeContentColor,
        dividerColor: Color = this.dividerColor,
    ) = DatePickerColors(
        containerColor.takeOrElse { this.containerColor },
        titleContentColor.takeOrElse { this.titleContentColor },
        headlineContentColor.takeOrElse { this.headlineContentColor },
        weekdayContentColor.takeOrElse { this.weekdayContentColor },
        subheadContentColor.takeOrElse { this.subheadContentColor },
        navigationContentColor.takeOrElse { this.navigationContentColor },
        yearContentColor.takeOrElse { this.yearContentColor },
        disabledYearContentColor.takeOrElse { this.disabledYearContentColor },
        currentYearContentColor.takeOrElse { this.currentYearContentColor },
        selectedYearContentColor.takeOrElse { this.selectedYearContentColor },
        disabledSelectedYearContentColor.takeOrElse { this.disabledSelectedYearContentColor },
        selectedYearContainerColor.takeOrElse { this.selectedYearContainerColor },
        disabledSelectedYearContainerColor.takeOrElse { this.disabledSelectedYearContainerColor },
        dayContentColor.takeOrElse { this.dayContentColor },
        disabledDayContentColor.takeOrElse { this.disabledDayContentColor },
        selectedDayContentColor.takeOrElse { this.selectedDayContentColor },
        disabledSelectedDayContentColor.takeOrElse { this.disabledSelectedDayContentColor },
        selectedDayContainerColor.takeOrElse { this.selectedDayContainerColor },
        disabledSelectedDayContainerColor.takeOrElse { this.disabledSelectedDayContainerColor },
        todayContentColor.takeOrElse { this.todayContentColor },
        todayDateBorderColor.takeOrElse { this.todayDateBorderColor },
        dayInSelectionRangeContainerColor.takeOrElse { this.dayInSelectionRangeContainerColor },
        dayInSelectionRangeContentColor.takeOrElse { this.dayInSelectionRangeContentColor },
        dividerColor.takeOrElse { this.dividerColor },
    )

    /**
     * Represents the content color for a calendar day.
     *
     * @param isToday indicates that the color is for a date that represents today
     * @param selected indicates that the color is for a selected day
     * @param inRange indicates that the day is part of a selection range of days
     * @param enabled indicates that the day is enabled for selection
     */
    @Composable
    internal fun dayContentColor(
        isToday: Boolean,
        selected: Boolean,
        inRange: Boolean,
        enabled: Boolean
    ): State<Color> {
        val target = when {
            selected && enabled -> selectedDayContentColor
            selected && !enabled -> disabledSelectedDayContentColor
            inRange && enabled -> dayInSelectionRangeContentColor
            inRange && !enabled -> disabledDayContentColor
            isToday -> todayContentColor
            enabled -> dayContentColor
            else -> disabledDayContentColor
        }
        return if (inRange) {
            rememberUpdatedState(target)
        } else {
            // Animate the content color only when the day is not in a range.
            animateColorAsState(
                target,
                tween(durationMillis = MotionTokens.DurationShort2.toInt())
            )
        }
    }

    /**
     * Represents the container color for a calendar day.
     *
     * @param selected indicates that the color is for a selected day
     * @param enabled indicates that the day is enabled for selection
     * @param animate whether or not to animate a container color change
     */
    @Composable
    internal fun dayContainerColor(
        selected: Boolean,
        enabled: Boolean,
        animate: Boolean
    ): State<Color> {
        val target = if (selected) {
            if (enabled) selectedDayContainerColor else disabledSelectedDayContainerColor
        } else {
            Color.Transparent
        }
        return if (animate) {
            animateColorAsState(
                target,
                tween(durationMillis = MotionTokens.DurationShort2.toInt())
            )
        } else {
            rememberUpdatedState(target)
        }
    }

    /**
     * Represents the content color for a calendar year.
     *
     * @param currentYear indicates that the color is for a year that represents the current year
     * @param selected indicates that the color is for a selected year
     * @param enabled indicates that the year is enabled for selection
     */
    @Composable
    internal fun yearContentColor(
        currentYear: Boolean,
        selected: Boolean,
        enabled: Boolean
    ): State<Color> {
        val target = when {
            selected && enabled -> selectedYearContentColor
            selected && !enabled -> disabledSelectedYearContentColor
            currentYear -> currentYearContentColor
            enabled -> yearContentColor
            else -> disabledYearContentColor
        }
        return animateColorAsState(
            target,
            tween(durationMillis = MotionTokens.DurationShort2.toInt())
        )
    }

    /**
     * Represents the container color for a calendar year.
     *
     * @param selected indicates that the color is for a selected day
     * @param enabled indicates that the year is enabled for selection
     */
    @Composable
    internal fun yearContainerColor(selected: Boolean, enabled: Boolean): State<Color> {
        val target = if (selected) {
            if (enabled) selectedYearContainerColor else disabledSelectedYearContainerColor
        } else {
            Color.Transparent
        }
        return animateColorAsState(
            target,
            tween(durationMillis = MotionTokens.DurationShort2.toInt())
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is DatePickerColors) return false
        if (containerColor != other.containerColor) return false
        if (titleContentColor != other.titleContentColor) return false
        if (headlineContentColor != other.headlineContentColor) return false
        if (weekdayContentColor != other.weekdayContentColor) return false
        if (subheadContentColor != other.subheadContentColor) return false
        if (yearContentColor != other.yearContentColor) return false
        if (disabledYearContentColor != other.disabledYearContentColor) return false
        if (currentYearContentColor != other.currentYearContentColor) return false
        if (selectedYearContentColor != other.selectedYearContentColor) return false
        if (disabledSelectedYearContentColor != other.disabledSelectedYearContentColor) return false
        if (selectedYearContainerColor != other.selectedYearContainerColor) return false
        if (disabledSelectedYearContainerColor != other.disabledSelectedYearContainerColor)
            return false
        if (dayContentColor != other.dayContentColor) return false
        if (disabledDayContentColor != other.disabledDayContentColor) return false
        if (selectedDayContentColor != other.selectedDayContentColor) return false
        if (disabledSelectedDayContentColor != other.disabledSelectedDayContentColor) return false
        if (selectedDayContainerColor != other.selectedDayContainerColor) return false
        if (disabledSelectedDayContainerColor != other.disabledSelectedDayContainerColor) {
            return false
        }
        if (todayContentColor != other.todayContentColor) return false
        if (todayDateBorderColor != other.todayDateBorderColor) return false
        if (dayInSelectionRangeContainerColor != other.dayInSelectionRangeContainerColor) {
            return false
        }
        if (dayInSelectionRangeContentColor != other.dayInSelectionRangeContentColor) return false
        return true
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + titleContentColor.hashCode()
        result = 31 * result + headlineContentColor.hashCode()
        result = 31 * result + weekdayContentColor.hashCode()
        result = 31 * result + subheadContentColor.hashCode()
        result = 31 * result + yearContentColor.hashCode()
        result = 31 * result + disabledYearContentColor.hashCode()
        result = 31 * result + currentYearContentColor.hashCode()
        result = 31 * result + selectedYearContentColor.hashCode()
        result = 31 * result + disabledSelectedYearContentColor.hashCode()
        result = 31 * result + selectedYearContainerColor.hashCode()
        result = 31 * result + disabledSelectedYearContainerColor.hashCode()
        result = 31 * result + dayContentColor.hashCode()
        result = 31 * result + disabledDayContentColor.hashCode()
        result = 31 * result + selectedDayContentColor.hashCode()
        result = 31 * result + disabledSelectedDayContentColor.hashCode()
        result = 31 * result + selectedDayContainerColor.hashCode()
        result = 31 * result + disabledSelectedDayContainerColor.hashCode()
        result = 31 * result + todayContentColor.hashCode()
        result = 31 * result + todayDateBorderColor.hashCode()
        result = 31 * result + dayInSelectionRangeContainerColor.hashCode()
        result = 31 * result + dayInSelectionRangeContentColor.hashCode()
        return result
    }
}

/**
 * An abstract for the date pickers states.
 *
 * This base class common state properties and provides a base implementation that is extended by
 * the different state classes.
 *
 * @param initialDisplayedMonthMillis timestamp in _UTC_ milliseconds from the epoch that represents
 *   an initial selection of a month to be displayed to the user. In case `null` is provided, the
 *   displayed month would be the current one.
 * @param yearRange an [IntRange] that holds the year range that the date picker will be limited to
 * @param selectableDates a [SelectableDates] that is consulted to check if a date is allowed. In
 *   case a date is not allowed to be selected, it will appear disabled in the UI.
 * @param locale a locale that will be used when formatting dates, determining the input format,
 *   week-days, and more.
 * @throws [IllegalArgumentException] if the initial selected date or displayed month represent a
 *   year that is out of the year range.
 * @see rememberDatePickerState
 */
@Stable
internal abstract class BaseDatePickerStateImpl(
    initialDisplayedMonthMillis: Long?,
    val yearRange: IntRange,
    selectableDates: SelectableDates,
    val locale: CalendarLocale,
) {

    val calendarModel = createCalendarModel(locale)

    var selectableDates by mutableStateOf(selectableDates)

    private val _displayedMonth =
        mutableStateOf(
            if (initialDisplayedMonthMillis != null) {
                var month = calendarModel.getMonth(initialDisplayedMonthMillis)
                if (!yearRange.contains(month.year)) {
                    // The initial display month's year is out of the years range, so just set the
                    // displayed month to the current one.
                    month = calendarModel.getMonth(calendarModel.today)
                }
                month
            } else {
                // Set the displayed month to the current one.
                calendarModel.getMonth(calendarModel.today)
            }
        )

    var displayedMonthMillis: Long
        get() = _displayedMonth.value.startUtcTimeMillis
        set(monthMillis) {
            val month = calendarModel.getMonth(monthMillis)
            // Set the displayed month only if the month's year is within the years range.
            if (yearRange.contains(month.year)) {
                _displayedMonth.value = month
            }
        }
}

/**
 * A default implementation of the [DatePickerState]. See [rememberDatePickerState].
 *
 * @param initialSelectedDateMillis timestamp in _UTC_ milliseconds from the epoch that represents
 *   an initial selection of a date. Provide a `null` to indicate no selection. Note that the
 *   state's [selectedDateMillis] will provide a timestamp that represents the _start_ of the day,
 *   which may be different than the provided initialSelectedDateMillis.
 * @param initialDisplayedMonthMillis timestamp in _UTC_ milliseconds from the epoch that represents
 *   an initial selection of a month to be displayed to the user. In case `null` is provided, the
 *   displayed month would be the current one.
 * @param yearRange an [IntRange] that holds the year range that the date picker will be limited to
 * @param initialDisplayMode an initial [DisplayMode] that this state will hold
 * @param selectableDates a [SelectableDates] that is consulted to check if a date is allowed. In
 *   case a date is not allowed to be selected, it will appear disabled in the UI
 * @param locale a [CalendarLocale] to be used when formatting dates, determining the input format,
 *   and more
 * @throws [IllegalArgumentException] if the initial selected date or displayed month represent a
 *   year that is out of the year range.
 * @see rememberDatePickerState
 */
@Stable
private class DatePickerStateImpl(
    initialDisplayedMonthMillis: Long?,
    yearRange: IntRange,
    selectableDates: SelectableDates,
    locale: CalendarLocale,
) : BaseDatePickerStateImpl(initialDisplayedMonthMillis, yearRange, selectableDates, locale),
    DatePickerState {

    companion object {
        /**
         * The default [Saver] implementation for [DatePickerStateImpl].
         *
         * @param selectableDates a [SelectableDates] instance that is consulted to check if a date
         *   is allowed
         */
        fun Saver(
            selectableDates: SelectableDates,
            locale: CalendarLocale,
        ): Saver<DatePickerStateImpl, Any> = listSaver(
            save = {
                listOf(
                    it.displayedMonthMillis,
                    it.yearRange.first,
                    it.yearRange.last,
                )
            },
            restore = { value ->
                DatePickerStateImpl(
                    initialDisplayedMonthMillis = value[0] as Long?,
                    yearRange = IntRange(value[1] as Int, value[2] as Int),
                    selectableDates = selectableDates,
                    locale = locale
                )
            }
        )
    }
}

/**
 * A date formatter used by [DatePicker].
 *
 * The date formatter will apply the best possible localized form of the given skeleton and Locale.
 * A skeleton is similar to, and uses the same format characters as, a Unicode <a
 * href="http://www.unicode.org/reports/tr35/#Date_Format_Patterns">UTS #35</a> pattern.
 *
 * One difference is that order is irrelevant. For example, "MMMMd" will return "MMMM d" in the
 * `en_US` locale, but "d. MMMM" in the `de_CH` locale.
 *
 * @param yearSelectionSkeleton a date format skeleton used to format the date picker's year
 *   selection menu button (e.g. "March 2021").
 * @param selectedDateSkeleton a date format skeleton used to format a selected date (e.g. "Mar 27,
 *   2021")
 * @param selectedDateDescriptionSkeleton a date format skeleton used to format a selected date to
 *   be used as content description for screen readers (e.g. "Saturday, March 27, 2021")
 */
@Immutable
private class DatePickerFormatterImpl(
    val yearSelectionSkeleton: String,
    val selectedDateSkeleton: String,
    val selectedDateDescriptionSkeleton: String
) : DatePickerFormatter {

    // A map for caching formatter related results for better performance
    private val formatterCache = mutableMapOf<String, Any>()

    override fun formatMonthYear(monthMillis: Long?, locale: CalendarLocale): String? {
        if (monthMillis == null) return null
        return formatWithSkeleton(monthMillis, yearSelectionSkeleton, locale, formatterCache)
    }

    override fun formatDate(
        dateMillis: Long?,
        locale: CalendarLocale,
        forContentDescription: Boolean,
    ): String? {
        if (dateMillis == null) return null
        return formatWithSkeleton(
            dateMillis,
            if (forContentDescription) {
                selectedDateDescriptionSkeleton
            } else {
                selectedDateSkeleton
            },
            locale,
            formatterCache,
        )
    }

    override fun equals(other: Any?): Boolean {
        if (other !is DatePickerFormatterImpl) return false

        if (yearSelectionSkeleton != other.yearSelectionSkeleton) return false
        if (selectedDateSkeleton != other.selectedDateSkeleton) return false
        if (selectedDateDescriptionSkeleton != other.selectedDateDescriptionSkeleton) return false

        return true
    }

    override fun hashCode(): Int {
        var result = yearSelectionSkeleton.hashCode()
        result = 31 * result + selectedDateSkeleton.hashCode()
        result = 31 * result + selectedDateDescriptionSkeleton.hashCode()
        return result
    }
}

/**
 * A base container for the date picker and the date input. This container composes the top common
 * area of the UI, and accepts [content] for the actual calendar picker or text field input.
 */
@Composable
internal fun DateEntryContainer(
    modifier: Modifier,
    headline: (@Composable () -> Unit)?,
    colors: DatePickerColors,
    headlineTextStyle: TextStyle,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .sizeIn(minWidth = DatePickerModalTokens.ContainerWidth)
            .semantics {
                @Suppress("DEPRECATION")
                isContainer = true
            }
            .background(colors.containerColor)
    ) {
        DatePickerHeader(
            modifier = Modifier,
            headlineContentColor = colors.headlineContentColor,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                val horizontalArrangement = when {
                    headline != null -> Arrangement.Start
                    else -> Arrangement.End
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = horizontalArrangement,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (headline != null) {
                        ProvideTextStyle(value = headlineTextStyle) {
                            Box(modifier = Modifier.weight(1f)) {
                                headline()
                            }
                        }
                    }
                }
                // Display a divider only when there is a title, headline, or a mode toggle.
                if (headline != null) HorizontalDivider(color = colors.dividerColor)
            }
        }
        content()
    }
}

@Composable
private fun DatePickerContent(
    selectedDatesMillis: List<Long>,
    displayedMonthMillis: Long,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    onDisplayedMonthChange: (monthInMillis: Long) -> Unit,
    calendarModel: CalendarModel,
    yearRange: IntRange,
    yearPickerEnabled: Boolean,
    dateFormatter: DatePickerFormatter,
    selectableDates: SelectableDates,
    colors: DatePickerColors,
    showEmptyWeek: Boolean,
) {
    val displayedMonth = calendarModel.getMonth(displayedMonthMillis)
    val monthsListState = rememberLazyListState(initialFirstVisibleItemIndex = displayedMonth.indexIn(yearRange))
    val coroutineScope = rememberCoroutineScope()
    var yearPickerVisible by rememberSaveable { mutableStateOf(false) }
    val defaultLocale = defaultLocale()
    Column {
        MonthsNavigation(
            modifier = Modifier.padding(horizontal = DatePickerHorizontalPadding),
            nextAvailable = monthsListState.canScrollForward,
            previousAvailable = monthsListState.canScrollBackward,
            yearPickerEnabled = yearPickerEnabled,
            yearPickerVisible = yearPickerVisible,
            yearPickerText = dateFormatter.formatMonthYear(
                monthMillis = displayedMonthMillis,
                locale = defaultLocale
            ) ?: "-",
            onNextClicked = {
                coroutineScope.launch {
                    try {
                        monthsListState.animateScrollToItem(monthsListState.firstVisibleItemIndex + 1)
                    } catch (_: IllegalArgumentException) {
                        // Ignore. This may happen if the user clicked the "next" arrow fast while
                        // the list was still animating to the next item.
                    }
                }
            },
            onPreviousClicked = {
                coroutineScope.launch {
                    try {
                        monthsListState.animateScrollToItem(monthsListState.firstVisibleItemIndex - 1)
                    } catch (_: IllegalArgumentException) {
                        // Ignore. This may happen if the user clicked the "previous" arrow fast
                        // while  the list was still animating to the previous item.
                    }
                }
            },
            onYearPickerButtonClicked = { yearPickerVisible = !yearPickerVisible },
            colors = colors
        )
        Box {
            Column(modifier = Modifier.padding(horizontal = DatePickerHorizontalPadding)) {
                WeekDays(colors, calendarModel)
                HorizontalMonthsList(
                    lazyListState = monthsListState,
                    selectedDatesMillis = selectedDatesMillis,
                    onDateSelectionChange = onDateSelectionChange,
                    onDisplayedMonthChange = onDisplayedMonthChange,
                    calendarModel = calendarModel,
                    yearRange = yearRange,
                    dateFormatter = dateFormatter,
                    selectableDates = selectableDates,
                    colors = colors,
                    showEmptyWeek = showEmptyWeek,
                )
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = yearPickerVisible,
                modifier = Modifier.clipToBounds(),
                enter = expandVertically() + fadeIn(initialAlpha = 0.6f),
                exit = shrinkVertically() + fadeOut()
            ) {
                // Apply a paneTitle to make the screen reader focus on a relevant node after this
                // column is hidden and disposed.
                // TODO(b/186443263): Have the screen reader focus on a year in the list when the
                //  list is revealed.
                val yearsPaneTitle = "date_picker_year_picker_pane_title"
                Column(modifier = Modifier.semantics { paneTitle = yearsPaneTitle }) {
                    YearPicker(
                        // Keep the height the same as the monthly calendar + weekdays height, and
                        // take into account the thickness of the divider that will be composed
                        // below it.
                        modifier = Modifier
                            .requiredHeight(RecommendedSizeForAccessibility * (MaxCalendarRows + 1) - 1.dp/* Divider thickness */)
                            .padding(horizontal = DatePickerHorizontalPadding),
                        displayedMonthMillis = displayedMonthMillis,
                        onYearSelected = { year ->
                            // Switch back to the monthly calendar and scroll to the selected year.
                            yearPickerVisible = !yearPickerVisible
                            coroutineScope.launch {
                                // Scroll to the selected year (maintaining the month of year).
                                // A LaunchEffect at the MonthsList will take care of rest and will
                                // update the state's displayedMonth to the month we scrolled to.
                                monthsListState.scrollToItem((year - yearRange.first) * 12 + displayedMonth.month - 1)
                            }
                        },
                        selectableDates = selectableDates,
                        calendarModel = calendarModel,
                        yearRange = yearRange,
                        colors = colors
                    )
                    HorizontalDivider(color = colors.dividerColor)
                }
            }
        }
    }
}

@Composable
internal fun DatePickerHeader(
    modifier: Modifier,
    headlineContentColor: Color,
    content: @Composable () -> Unit
) {
    // Apply a defaultMinSize only when the title is not null.
    val heightModifier = Modifier
    Column(
        modifier
            .fillMaxWidth()
            .then(heightModifier),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(
            LocalContentColor provides headlineContentColor, content = content
        )
    }
}

/**
 * Composes a horizontal pageable list of months.
 */
@Composable
private fun HorizontalMonthsList(
    lazyListState: LazyListState,
    selectedDatesMillis: List<Long>,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    onDisplayedMonthChange: (monthInMillis: Long) -> Unit,
    calendarModel: CalendarModel,
    yearRange: IntRange,
    dateFormatter: DatePickerFormatter,
    selectableDates: SelectableDates,
    colors: DatePickerColors,
    showEmptyWeek: Boolean,
) {
    val today = calendarModel.today
    val firstMonth = remember(yearRange) {
        calendarModel.getMonth(
            year = yearRange.first,
            month = 1 // January
        )
    }
    ProvideTextStyle(WhoppahTheme.typography.labelLegacy) {
        LazyRow(
            // Apply this to prevent the screen reader from scrolling to the next or previous month,
            // and instead, traverse outside the Month composable when swiping from a focused first
            // or last day of the month.
            modifier = Modifier.semantics {
                horizontalScrollAxisRange = ScrollAxisRange(value = { 0f }, maxValue = { 0f })
            },
            state = lazyListState,
            flingBehavior = DatePickerDefaults.rememberSnapFlingBehavior(lazyListState)
        ) {
            items(numberOfMonthsInRange(yearRange)) {
                val month = calendarModel.plusMonths(
                    from = firstMonth,
                    addedMonthsCount = it
                )
                Box(
                    modifier = Modifier.fillParentMaxWidth()
                ) {
                    Month(
                        month = month,
                        onDateSelectionChange = onDateSelectionChange,
                        todayMillis = today.utcTimeMillis,
                        selectedDatesMillis = selectedDatesMillis,
                        rangeSelectionInfo = null,
                        dateFormatter = dateFormatter,
                        selectableDates = selectableDates,
                        colors = colors,
                        showEmptyWeek = showEmptyWeek,
                    )
                }
            }
        }
    }
    LaunchedEffect(lazyListState) {
        updateDisplayedMonth(
            lazyListState = lazyListState,
            onDisplayedMonthChange = onDisplayedMonthChange,
            calendarModel = calendarModel,
            yearRange = yearRange
        )
    }
}

internal suspend fun updateDisplayedMonth(
    lazyListState: LazyListState,
    onDisplayedMonthChange: (monthInMillis: Long) -> Unit,
    calendarModel: CalendarModel,
    yearRange: IntRange
) {
    snapshotFlow { lazyListState.firstVisibleItemIndex }.collect {
        val yearOffset = lazyListState.firstVisibleItemIndex / 12
        val month = lazyListState.firstVisibleItemIndex % 12 + 1
        onDisplayedMonthChange(
            calendarModel.getMonth(
                year = yearRange.first + yearOffset,
                month = month
            ).startUtcTimeMillis
        )
    }
}

/**
 * Composes the weekdays letters.
 */
@Composable
internal fun WeekDays(colors: DatePickerColors, calendarModel: CalendarModel) {
    val firstDayOfWeek = calendarModel.firstDayOfWeek
    val weekdays = calendarModel.weekdayNames
    val dayNames = arrayListOf<Pair<String, String>>()
    // Start with firstDayOfWeek - 1 as the days are 1-based.
    for (i in firstDayOfWeek - 1 until weekdays.size) {
        dayNames.add(weekdays[i])
    }
    for (i in 0 until firstDayOfWeek - 1) {
        dayNames.add(weekdays[i])
    }
    val textStyle = WhoppahTheme.typography.labelLegacy
    Row(
        modifier = Modifier
            .defaultMinSize(
                minHeight = RecommendedSizeForAccessibility
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dayNames.fastForEach {
            Box(
                modifier = Modifier
                    .clearAndSetSemantics { contentDescription = it.first }
                    .size(
                        width = RecommendedSizeForAccessibility,
                        height = RecommendedSizeForAccessibility
                    ),
                contentAlignment = Alignment.Center) {
                Text(
                    text = it.second,
                    modifier = Modifier.wrapContentSize(),
                    color = colors.weekdayContentColor,
                    style = textStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * A composable that renders a calendar month and displays a date selection.
 */
@Composable
internal fun Month(
    month: CalendarMonth,
    onDateSelectionChange: (dateInMillis: Long) -> Unit,
    todayMillis: Long,
    selectedDatesMillis: List<Long>,
    rangeSelectionInfo: SelectedRangeInfo?,
    dateFormatter: DatePickerFormatter,
    selectableDates: SelectableDates,
    colors: DatePickerColors,
    showEmptyWeek: Boolean,
) {
    val rangeSelectionDrawModifier = if (rangeSelectionInfo != null) {
        Modifier.drawWithContent {
            drawRangeBackground(rangeSelectionInfo, colors.dayInSelectionRangeContainerColor)
            drawContent()
        }
    } else {
        Modifier
    }
    val defaultLocale = defaultLocale()
    var cellIndex = 0
    Column(
        modifier = Modifier
            .requiredHeight(RecommendedSizeForAccessibility * MaxCalendarRows)
            .then(rangeSelectionDrawModifier),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        for (weekIndex in 0 until MaxCalendarRows) {
            if (!showEmptyWeek) {
                val allWeekEmpty = (cellIndex..cellIndex + 6).all {
                    it < month.daysFromStartOfWeekToFirstOfMonth || it >= (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                }
                if (allWeekEmpty) break
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (dayIndex in 0 until DaysInWeek) {
                    if (cellIndex < month.daysFromStartOfWeekToFirstOfMonth ||
                        cellIndex >= (month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays)
                    ) {
                        // Empty cell
                        Spacer(
                            modifier = Modifier.requiredSize(
                                width = RecommendedSizeForAccessibility,
                                height = RecommendedSizeForAccessibility
                            )
                        )
                    } else {
                        val dayNumber = cellIndex - month.daysFromStartOfWeekToFirstOfMonth
                        val dateInMillis = month.startUtcTimeMillis + (dayNumber * MillisecondsIn24Hours)
                        val isToday = dateInMillis == todayMillis
                        val startDateSelected = selectedDatesMillis.contains(dateInMillis)
                        val endDateSelected = false
                        val inRange = false
                        val dayContentDescription = dayContentDescription(
                            rangeSelectionEnabled = rangeSelectionInfo != null,
                            isToday = isToday,
                            isStartDate = startDateSelected,
                            isEndDate = endDateSelected,
                            isInRange = inRange
                        )
                        val formattedDateDescription = dateFormatter.formatDate(
                            dateInMillis,
                            defaultLocale,
                            forContentDescription = true
                        ) ?: ""
                        Day(
                            modifier = Modifier,
                            selected = startDateSelected || endDateSelected,
                            onClick = { onDateSelectionChange(dateInMillis) },
                            // Only animate on the first selected day. This is important to
                            // disable when drawing a range marker behind the days on an
                            // end-date selection.
                            animateChecked = startDateSelected,
                            enabled = remember(dateInMillis) {
                                // Disabled a day in case its year is not selectable, or the
                                // date itself is specifically not allowed by the state's
                                // SelectableDates.
                                with(selectableDates) {
                                    isSelectableYear(month.year) &&
                                            isSelectableDate(dateInMillis)
                                }
                            },
                            today = isToday,
                            inRange = inRange,
                            description = if (dayContentDescription != null) "$dayContentDescription, $formattedDateDescription" else formattedDateDescription,
                            colors = colors
                        ) {
                            Text(
                                text = (dayNumber + 1).toLocalString(),
                                // The semantics are set at the Day level.
                                modifier = Modifier.clearAndSetSemantics { },
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    cellIndex++
                }
            }
        }
    }
}

/**
 * Returns the number of months within the given year range.
 */
internal fun numberOfMonthsInRange(yearRange: IntRange) = (yearRange.last - yearRange.first + 1) * 12

@Composable
private fun dayContentDescription(
    rangeSelectionEnabled: Boolean,
    isToday: Boolean,
    isStartDate: Boolean,
    isEndDate: Boolean,
    isInRange: Boolean
): String? {
    val descriptionBuilder = StringBuilder()
    if (rangeSelectionEnabled) {
        when {
            isStartDate -> descriptionBuilder.append("date_range_picker_start_headline")
            isEndDate -> descriptionBuilder.append("date_range_picker_end_headline")
            isInRange -> descriptionBuilder.append("date_range_picker_day_in_range")
        }
    }
    if (isToday) {
        if (descriptionBuilder.isNotEmpty()) descriptionBuilder.append(", ")
        descriptionBuilder.append("date_picker_today_description")
    }
    return if (descriptionBuilder.isEmpty()) null else descriptionBuilder.toString()
}

@Composable
private fun Day(
    modifier: Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    animateChecked: Boolean,
    enabled: Boolean,
    today: Boolean,
    inRange: Boolean,
    description: String,
    colors: DatePickerColors,
    content: @Composable () -> Unit
) {
    Surface(
        selected = selected,
        onClick = onClick,
        modifier = modifier
            // Apply and merge semantics here. This will ensure that when scrolling the list the
            // entire Day surface is treated as one unit and holds the date semantics even when it's
            // not completely visible atm.
            .semantics(mergeDescendants = true) {
                text = AnnotatedString(description)
                role = Role.Button
            },
        enabled = enabled,
        shape = CircleShape,
        color = colors.dayContainerColor(
            selected = selected,
            enabled = enabled,
            animate = animateChecked
        ).value,
        contentColor = colors.dayContentColor(
            isToday = today,
            selected = selected,
            inRange = inRange,
            enabled = enabled,
        ).value,
        border = if (today && !selected) {
            BorderStroke(
                DatePickerModalTokens.DateTodayContainerOutlineWidth,
                colors.todayDateBorderColor
            )
        } else {
            null
        }
    ) {
        Box(
            modifier = Modifier.requiredSize(
                DatePickerModalTokens.DateStateLayerWidth,
                DatePickerModalTokens.DateStateLayerHeight
            ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Composable
private fun YearPicker(
    modifier: Modifier,
    displayedMonthMillis: Long,
    onYearSelected: (year: Int) -> Unit,
    selectableDates: SelectableDates,
    calendarModel: CalendarModel,
    yearRange: IntRange,
    colors: DatePickerColors
) {
    ProvideTextStyle(value = WhoppahTheme.typography.labelLegacy) {
        val currentYear = calendarModel.getMonth(calendarModel.today).year
        val displayedYear = calendarModel.getMonth(displayedMonthMillis).year
        val lazyGridState =
            rememberLazyGridState(
                // Set the initial index to a few years before the current year to allow quicker
                // selection of previous years.
                initialFirstVisibleItemIndex = max(
                    0, displayedYear - yearRange.first - YearsInRow
                )
            )
        // Match the years container color to any elevated surface color that is composed under it.
        val containerColor = colors.containerColor
        val coroutineScope = rememberCoroutineScope()
        val scrollToEarlierYearsLabel = "date_picker_scroll_to_earlier_years"
        val scrollToLaterYearsLabel = "date_picker_scroll_to_later_years"
        LazyVerticalGrid(
            columns = GridCells.Fixed(YearsInRow),
            modifier = modifier
                .background(containerColor)
                // Apply this to have the screen reader traverse outside the visible list of years
                // and not scroll them by default.
                .semantics {
                    verticalScrollAxisRange = ScrollAxisRange(value = { 0f }, maxValue = { 0f })
                },
            state = lazyGridState,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalArrangement = Arrangement.spacedBy(YearsVerticalPadding)
        ) {
            items(yearRange.count()) {
                val selectedYear = it + yearRange.first
                val localizedYear = selectedYear.toLocalString()
                Year(
                    modifier = Modifier
                        .requiredSize(
                            width = DatePickerModalTokens.SelectionYearContainerWidth,
                            height = DatePickerModalTokens.SelectionYearContainerHeight
                        )
                        .semantics {
                            // Apply a11y custom actions to the first and last items in the years
                            // grid. The actions will suggest to scroll to earlier or later years in
                            // the grid.
                            customActions = if (lazyGridState.firstVisibleItemIndex == it ||
                                lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == it
                            ) {
                                customScrollActions(
                                    state = lazyGridState,
                                    coroutineScope = coroutineScope,
                                    scrollUpLabel = scrollToEarlierYearsLabel,
                                    scrollDownLabel = scrollToLaterYearsLabel
                                )
                            } else {
                                emptyList()
                            }
                        },
                    selected = selectedYear == displayedYear,
                    currentYear = selectedYear == currentYear,
                    onClick = { onYearSelected(selectedYear) },
                    enabled = selectableDates.isSelectableYear(selectedYear),
                    description = "date_picker_navigate_to_year_description $localizedYear",
                    colors = colors
                ) {
                    Text(
                        text = localizedYear,
                        // The semantics are set at the Year level.
                        modifier = Modifier.clearAndSetSemantics {},
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun Year(
    modifier: Modifier,
    selected: Boolean,
    currentYear: Boolean,
    onClick: () -> Unit,
    enabled: Boolean,
    description: String,
    colors: DatePickerColors,
    content: @Composable () -> Unit
) {
    val border = remember(currentYear, selected) {
        if (currentYear && !selected) {
            // Use the day's spec to draw a border around the current year.
            BorderStroke(1.dp, colors.todayDateBorderColor)
        } else {
            null
        }
    }
    Surface(
        selected = selected,
        onClick = onClick,
        // Apply and merge semantics here. This will ensure that when scrolling the list the entire
        // Year surface is treated as one unit and holds the date semantics even when it's not
        // completely visible atm.
        modifier = modifier.semantics(mergeDescendants = true) {
            text = AnnotatedString(description)
            role = Role.Button
        },
        enabled = enabled,
        shape = CircleShape,
        color = colors.yearContainerColor(selected = selected, enabled = enabled).value,
        contentColor = colors.yearContentColor(
            currentYear = currentYear,
            selected = selected,
            enabled = enabled
        ).value,
        border = border,
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            content()
        }
    }
}

/**
 * A composable that shows a year menu button and a couple of buttons that enable navigation between
 * displayed months.
 */
@Composable
private fun MonthsNavigation(
    modifier: Modifier,
    nextAvailable: Boolean,
    previousAvailable: Boolean,
    yearPickerEnabled: Boolean,
    yearPickerVisible: Boolean,
    yearPickerText: String,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onYearPickerButtonClicked: () -> Unit,
    colors: DatePickerColors
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(MonthYearHeight),
        horizontalArrangement = if (yearPickerVisible) {
            Arrangement.Start
        } else {
            Arrangement.SpaceBetween
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.navigationContentColor) {
            // Show arrows for traversing months (only visible when the year selection is off)
            if (!yearPickerVisible)
                IconButton(onClick = onPreviousClicked, enabled = previousAvailable) {
                    Icon(
                        imageVector = WhIcons.Navigation.KeyboardArrowLeft,
                        contentDescription = null,
                    )
                }
            // A menu button for selecting a year.
            YearPickerMenuButton(
                onClick = onYearPickerButtonClicked,
                yearPickerEnabled = yearPickerEnabled,
                expanded = yearPickerVisible
            ) {
                Text(
                    text = yearPickerText,
                    modifier = Modifier.semantics {
                        // Make the screen reader read out updates to the menu button text as the
                        // user navigates the arrows or scrolls to change the displayed month.
                        liveRegion = LiveRegionMode.Polite
                        contentDescription = yearPickerText
                    })
            }
            // Show arrows for traversing months (only visible when the year selection is off)
            if (!yearPickerVisible)
                IconButton(onClick = onNextClicked, enabled = nextAvailable) {
                    Icon(
                        imageVector = WhIcons.Navigation.KeyboardArrowRight,
                        contentDescription = null,
                    )
                }
        }
    }
}

// TODO: Replace with the official MenuButton when implemented.
@Composable
private fun YearPickerMenuButton(
    onClick: () -> Unit,
    yearPickerEnabled: Boolean,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    TextButton(
        onClick = { if (yearPickerEnabled) onClick() },
        modifier = modifier,
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(contentColor = LocalContentColor.current),
        elevation = null,
        border = null,
    ) {
        content()
        if (yearPickerEnabled) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                imageVector = WhIcons.Navigation.MenuChevronDown,
                contentDescription = null,
                modifier = Modifier.rotate(if (expanded) 180f else 0f)
            )
        }
    }
}

private fun customScrollActions(
    state: LazyGridState,
    coroutineScope: CoroutineScope,
    scrollUpLabel: String,
    scrollDownLabel: String
): List<CustomAccessibilityAction> {
    val scrollUpAction = {
        if (!state.canScrollBackward) {
            false
        } else {
            coroutineScope.launch {
                state.scrollToItem(state.firstVisibleItemIndex - YearsInRow)
            }
            true
        }
    }
    val scrollDownAction = {
        if (!state.canScrollForward) {
            false
        } else {
            coroutineScope.launch {
                state.scrollToItem(state.firstVisibleItemIndex + YearsInRow)
            }
            true
        }
    }
    return listOf(
        CustomAccessibilityAction(
            label = scrollUpLabel,
            action = scrollUpAction
        ),
        CustomAccessibilityAction(
            label = scrollDownLabel,
            action = scrollDownAction
        )
    )
}

internal val RecommendedSizeForAccessibility = 48.dp
internal val MonthYearHeight = 56.dp
internal val DatePickerHorizontalPadding = 0.dp
private val DatePickerTitlePadding = PaddingValues(start = 24.dp, end = 12.dp, top = 16.dp)
private val DatePickerHeadlinePadding = PaddingValues(start = 24.dp, end = 12.dp, bottom = 12.dp)
private val YearsVerticalPadding = 16.dp
private const val MaxCalendarRows = 6
private const val YearsInRow: Int = 3

private object MotionTokens {
    const val DurationShort2 = 100.0
}

private object DatePickerModalTokens {
    val ContainerWidth = 360.0.dp
    val SelectionYearContainerHeight = 36.0.dp
    val SelectionYearContainerWidth = 72.0.dp
    val DateStateLayerHeight = 40.0.dp
    val DateStateLayerWidth = 40.0.dp
    val DateTodayContainerOutlineWidth = 1.0.dp
}


/**
 * a helper class for drawing a range selection. The class holds information about the selected
 * start and end dates as coordinates within the 7 x 6 calendar month grid, as well as information
 * regarding the first and last selected items.
 *
 * A SelectedRangeInfo is created when a [Month] is composed with an `rangeSelectionEnabled` flag.
 */
internal class SelectedRangeInfo(
    val gridStartCoordinates: IntOffset,
    val gridEndCoordinates: IntOffset,
    val firstIsSelectionStart: Boolean,
    val lastIsSelectionEnd: Boolean
) {
    companion object {
        /**
         * Calculates the selection coordinates within the current month's grid. The returned [Pair]
         * holds the actual item x & y coordinates within the LazyVerticalGrid, and is later used to
         * calculate the exact offset for drawing the selection rectangles when in range-selection
         * mode.
         */
        fun calculateRangeInfo(
            month: CalendarMonth,
            startDate: CalendarDate,
            endDate: CalendarDate
        ): SelectedRangeInfo? {
            if (startDate.utcTimeMillis > month.endUtcTimeMillis ||
                endDate.utcTimeMillis < month.startUtcTimeMillis
            ) {
                return null
            }
            val firstIsSelectionStart = startDate.utcTimeMillis >= month.startUtcTimeMillis
            val lastIsSelectionEnd = endDate.utcTimeMillis <= month.endUtcTimeMillis
            val startGridItemOffset = if (firstIsSelectionStart) {
                month.daysFromStartOfWeekToFirstOfMonth + startDate.dayOfMonth - 1
            } else {
                month.daysFromStartOfWeekToFirstOfMonth
            }
            val endGridItemOffset = if (lastIsSelectionEnd) {
                month.daysFromStartOfWeekToFirstOfMonth + endDate.dayOfMonth - 1
            } else {
                month.daysFromStartOfWeekToFirstOfMonth + month.numberOfDays - 1
            }
            // Calculate the selected coordinates within the cells grid.
            val gridStartCoordinates = IntOffset(
                x = startGridItemOffset % DaysInWeek,
                y = startGridItemOffset / DaysInWeek
            )
            val gridEndCoordinates = IntOffset(
                x = endGridItemOffset % DaysInWeek,
                y = endGridItemOffset / DaysInWeek
            )
            return SelectedRangeInfo(
                gridStartCoordinates,
                gridEndCoordinates,
                firstIsSelectionStart,
                lastIsSelectionEnd
            )
        }
    }
}

/**
 * Draws the range selection background.
 *
 * This function is called during a [Modifier.drawWithContent] call when a [Month] is composed with
 * an `rangeSelectionEnabled` flag.
 */
internal fun ContentDrawScope.drawRangeBackground(
    selectedRangeInfo: SelectedRangeInfo,
    color: Color
) {
    // The LazyVerticalGrid is defined to space the items horizontally by
    // DaysHorizontalPadding (e.g. 4.dp). However, as the grid is not limited in
    // width, the spacing can go beyond that value, so this drawing takes this into
    // account.
    // TODO: Use the date's container width and height from the tokens once b/247694457 is resolved.
    val itemContainerWidth = RecommendedSizeForAccessibility.toPx()
    val itemContainerHeight = RecommendedSizeForAccessibility.toPx()
    val itemStateLayerHeight = DatePickerModalTokens.DateStateLayerHeight.toPx()
    val stateLayerVerticalPadding = (itemContainerHeight - itemStateLayerHeight) / 2
    val horizontalSpaceBetweenItems =
        (this.size.width - DaysInWeek * itemContainerWidth) / DaysInWeek
    val (x1, y1) = selectedRangeInfo.gridStartCoordinates
    val (x2, y2) = selectedRangeInfo.gridEndCoordinates
    // The endX and startX are offset to include only half the item's width when dealing with first
    // and last items in the selection in order to keep the selection edges rounded.
    var startX = x1 * (itemContainerWidth + horizontalSpaceBetweenItems) +
            (if (selectedRangeInfo.firstIsSelectionStart) itemContainerWidth / 2 else 0f) +
            horizontalSpaceBetweenItems / 2
    val startY = y1 * itemContainerHeight + stateLayerVerticalPadding
    var endX = x2 * (itemContainerWidth + horizontalSpaceBetweenItems) +
            (if (selectedRangeInfo.lastIsSelectionEnd) itemContainerWidth / 2 else itemContainerWidth) +
            horizontalSpaceBetweenItems / 2
    val endY = y2 * itemContainerHeight + stateLayerVerticalPadding
    val isRtl = layoutDirection == LayoutDirection.Rtl
    // Adjust the start and end in case the layout is RTL.
    if (isRtl) {
        startX = this.size.width - startX
        endX = this.size.width - endX
    }
    // Draw the first row background
    drawRect(
        color = color,
        topLeft = Offset(startX, startY),
        size = Size(
            width = when {
                y1 == y2 -> endX - startX
                isRtl -> -startX
                else -> this.size.width - startX
            },
            height = itemStateLayerHeight
        )
    )
    if (y1 != y2) {
        for (y in y2 - y1 - 1 downTo 1) {
            // Draw background behind the rows in between.
            drawRect(
                color = color,
                topLeft = Offset(0f, startY + (y * itemContainerHeight)),
                size = Size(
                    width = this.size.width,
                    height = itemStateLayerHeight
                )
            )
        }
        // Draw the last row selection background
        val topLeftX = if (layoutDirection == LayoutDirection.Ltr) 0f else this.size.width
        drawRect(
            color = color,
            topLeft = Offset(topLeftX, endY),
            size = Size(
                width = if (isRtl) endX - this.size.width else endX,
                height = itemStateLayerHeight
            )
        )
    }
}