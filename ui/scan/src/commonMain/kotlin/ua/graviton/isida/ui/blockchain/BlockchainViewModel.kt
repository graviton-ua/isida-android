package ua.crypto.ui.blockchain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import ua.crypto.core.util.AppCoroutineDispatchers
import ua.crypto.domain.models.BlockchainDomainEvent
import ua.crypto.domain.observers.ObserveBlockchainEvents
import ua.crypto.ui.di.ViewModelKey
import ua.crypto.ui.di.ViewModelScope

@OptIn(FlowPreview::class)
@ContributesIntoMap(ViewModelScope::class)
@ViewModelKey(BlockchainViewModel::class)
@Inject
class BlockchainViewModel(
    dispatchers: AppCoroutineDispatchers,
    observer: ObserveBlockchainEvents,
) : ViewModel() {

    private val feed = observer.flow
        .map { items -> items.map(BlockchainDomainEvent::toUiModel) }
        .flowOn(dispatchers.computation)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())


    val state: StateFlow<BlockchainViewState> = feed.map { feed ->
        BlockchainViewState(
            feed = feed,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BlockchainViewState.Init,
    )


    init {
        observer()
    }
}