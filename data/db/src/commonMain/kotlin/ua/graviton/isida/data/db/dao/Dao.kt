package ua.graviton.isida.data.db.dao

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import ua.graviton.isida.data.sql.Database

abstract class Dao(
    protected val dispatcher: CoroutineDispatcher,
    protected val db: Database,
) {
    suspend fun <T> transaction(block: Database.() -> T): T = withContext(dispatcher) { db.transactionWithResult { block(db) } }
}

abstract class FlowDao<R>(
    dispatcher: CoroutineDispatcher,
    db: Database,
) : Dao(dispatcher = dispatcher, db = db) {

    // SharedFlow to notify updates
    private val _updateFlow = MutableSharedFlow<R?>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val updateFlow: SharedFlow<R?> = _updateFlow

    protected suspend fun notifyUpdate(r: R? = null) = _updateFlow.emit(r)

    protected suspend fun defaultFilter(value: R?): Boolean = true // Regular suspend function

    @OptIn(ExperimentalCoroutinesApi::class)
    protected inline fun <reified T> observeWithUpdates(
        crossinline filter: suspend (R?) -> Boolean = ::defaultFilter,
        crossinline fetch: suspend () -> T,
    ): Flow<T> {
        return updateFlow
            .onStart { emit(null) } // Initial trigger
            .filter(filter)
            .flatMapLatest {
                flow { emit(fetch()) } // Fetch data on each update
            }
            .distinctUntilChanged() // Avoid duplicate emissions
    }
}