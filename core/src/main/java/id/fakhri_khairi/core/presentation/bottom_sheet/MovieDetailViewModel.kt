package id.fakhri_khairi.core.presentation.bottom_sheet

import dagger.hilt.android.lifecycle.HiltViewModel
import id.fakhri_khairi.core.base.BaseViewModel
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import id.fakhri_khairi.domain.Video
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

sealed class MovieDetailState {
    object Idle : MovieDetailState()
}

sealed class MovieDetailEvent {
    data class SuccessGetMovieRecommendation(val data: List<Movie>) : MovieDetailEvent()
    data class Success(val data: Video) : MovieDetailEvent()
    data class Error(val message: String) : MovieDetailEvent()
    object Empty : MovieDetailEvent()
}

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    coroutineContext: CoroutineContext,
    private val useCase: MovieDetailUseCase
) : BaseViewModel<MovieDetailState, MovieDetailEvent>(
    MovieDetailState.Idle,
    coroutineContext
) {

    fun getMovieVideo(movieId: Int) {
        adaptiveScope.launch {
            when(val result = useCase.getMovieVideo.execute(movieId)) {
                is Result.Error -> eventChannel.send(MovieDetailEvent.Error(result.message))
                is Result.Success -> {
                    val resultVideo = result.data.firstOrNull { it.official }

                    if (resultVideo?.key?.isEmpty() == true) return@launch eventChannel.send(MovieDetailEvent.Empty)

                    resultVideo?.let { eventChannel.send(MovieDetailEvent.Success(it)) }
                }
            }
        }
    }

    fun getMovieRecommendation(movieId: Int) {
        adaptiveScope.launch {
            when(val result = useCase.getMovieRecommendation.execute(movieId)) {
                is Result.Error -> eventChannel.send(MovieDetailEvent.Error(result.message))
                is Result.Success -> eventChannel.send(MovieDetailEvent.SuccessGetMovieRecommendation(result.data))
            }
        }
    }
}