package ua.isida.ui.setday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.*
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactoryKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ua.isida.data.protocol.packets.TableDay
import ua.isida.data.protocol.packets.v1.TableDayV1
import ua.isida.ui.setday.models.*
import ua.isida.util.AppCoroutineDispatchers

@AssistedInject
class SetDayViewModel(
    @Assisted private val index: Int,
    @Assisted private val day: TableDay,
    dispatchers: AppCoroutineDispatchers,
) : ViewModel() {

    @AssistedFactory
    @ManualViewModelAssistedFactoryKey
    @ContributesIntoMap(AppScope::class)
    interface Factory : ManualViewModelAssistedFactory {
        fun create(index: Int, day: TableDay): SetDayViewModel
    }

    private val _events = Channel<SetDayViewEvent>(Channel.BUFFERED)
    val events: Flow<SetDayViewEvent> = _events.receiveAsFlow()

    private val properties = initAllProperties(day)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dataIsValid = combine(properties.map { it.isValid }) { a -> a.all { it } }

    val state: StateFlow<SetDayViewState> = dataIsValid.map { dataIsValid ->
        SetDayViewState(
            properties = properties,
            dataIsValid = dataIsValid,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SetDayViewState(properties = properties, dataIsValid = true),
    )


    init {
        // Clear error state if input is updated
        properties.forEach { property ->
            viewModelScope.launch(dispatchers.computation) { property.clearErrorOnInputUpdate() }
        }
    }


    fun submit() = viewModelScope.launch(Dispatchers.Default) {
        // validate prop before we send anything
        val isValid = properties.map { it.validate() }.all { it }

        // If it's not valid error already been shown on the UI, we can silently return
        if (!isValid) return@launch

        var snapshot = day
        properties.forEach { snapshot = it.copyAndUpdate(snapshot) }

        _events.send(SetDayViewEvent.OnSubmit(index, snapshot))
    }

    private fun initAllProperties(day: TableDay): List<TableDayProperty<*>> = when (day) {
        is TableDayV1 -> listOf(
            SpT0(value = day.spT0),
            SpT1(value = day.spT1),
            SpRh1(value = day.spRh),
            TurnPermission(value = day.spTr),
            FlapProgramDay(value = day.spFlp),
            PropertySpCO2(value = day.spCO2),
        )

        else -> throw IllegalArgumentException("Unsupported TableDay version")
    }
}