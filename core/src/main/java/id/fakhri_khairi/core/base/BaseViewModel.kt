package id.fakhri_khairi.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
abstract class BaseViewModel<STATE, EVENT>(
    initialState: STATE,
    private val coroutineContext: CoroutineContext
) : ViewModel() {
    protected val mutableState = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = mutableState

    protected val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    val event = eventChannel.receiveAsFlow()

    protected val adaptiveScope: CoroutineScope
        get() = viewModelScope + coroutineContext
}