package id.fakhri_khairi.core.presentation.search

import dagger.hilt.android.lifecycle.HiltViewModel
import id.fakhri_khairi.core.base.BaseViewModel
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

sealed class SearchMovieState {
    object Idle : SearchMovieState()
}

sealed class SearchMovieEvent {
    data class Success(val data : List<Movie>) : SearchMovieEvent()
    data class Error(val message : String) : SearchMovieEvent()
    object Empty : SearchMovieEvent()
}

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    coroutineContext: CoroutineContext,
    private val useCase: SearchMovieUseCase
) : BaseViewModel<SearchMovieState, SearchMovieEvent>(
    SearchMovieState.Idle,
    coroutineContext
) {

    fun searchMovie(query: String) {
        adaptiveScope.launch {
            when(val result = useCase.getSearchMovie.execute(query)) {
                is Result.Error -> eventChannel.send(SearchMovieEvent.Error(result.message))
                is Result.Success -> {
                    val result = result.data

                    if (result.isEmpty()) return@launch eventChannel.send(SearchMovieEvent.Empty)

                    eventChannel.send(SearchMovieEvent.Success(result))
                }
            }
        }
    }
}