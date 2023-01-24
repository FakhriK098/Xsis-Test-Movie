package id.fakhri_khairi.core.presentation.home

import dagger.hilt.android.lifecycle.HiltViewModel
import id.fakhri_khairi.core.base.BaseViewModel
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

sealed class HomeState {
    object Idle : HomeState()
    data class SuccessGetMovieGenre(val data : List<Genre>) : HomeState()
    data class SuccessGetPopularMovie(val data : List<Movie>) : HomeState()
    data class SuccessGetNowPlaying(val data: List<Movie>) : HomeState()
}

sealed class HomeEvent {
    data class Error(val message : String) : HomeEvent()
    data class SuccessGetMovieByGenre(val data: List<Movie>) : HomeEvent()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    coroutineContext: CoroutineContext,
    private val useCase: HomeUseCase
) : BaseViewModel<HomeState, HomeEvent>(
    HomeState.Idle,
    coroutineContext
) {

    fun getPopularMovie() {
        adaptiveScope.launch {
            when(val result = useCase.getPopularMovie.execute(
                page = 1
            )) {
                is Result.Error -> eventChannel.send(HomeEvent.Error(result.message))
                is Result.Success -> mutableState.emit(HomeState.SuccessGetPopularMovie(result.data))
            }
        }
    }

    fun getNowPlaying() {
        adaptiveScope.launch {
            when(val result = useCase.getNowPlayingMovie.execute(
                page = 1
            )) {
                is Result.Error -> eventChannel.send(HomeEvent.Error(result.message))
                is Result.Success -> mutableState.emit(HomeState.SuccessGetNowPlaying(result.data))
            }
        }
    }

    fun getGenreMovie() {
        adaptiveScope.launch {
            when(val result = useCase.getGenreMovie.execute()) {
                is Result.Error -> eventChannel.send(HomeEvent.Error(result.message))
                is Result.Success -> mutableState.emit(HomeState.SuccessGetMovieGenre(result.data))
            }
        }
    }

    fun getMovieByGenre(genreId: Int) {
        adaptiveScope.launch {
            when(val result = useCase.getMovieByGenre.execute(genreId, 1)) {
                is Result.Error -> eventChannel.send(HomeEvent.Error(result.message))
                is Result.Success -> eventChannel.send(HomeEvent.SuccessGetMovieByGenre(result.data))
            }
        }
    }
}