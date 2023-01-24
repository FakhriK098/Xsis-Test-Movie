package id.fakhri_khairi.core.presentation.home

import id.fakhri_khairi.data.usecase.contract.GetGenreMovie
import id.fakhri_khairi.data.usecase.contract.GetMovieByGenre
import id.fakhri_khairi.data.usecase.contract.GetNowPlayingMovie
import id.fakhri_khairi.data.usecase.contract.GetPopularMovie
import javax.inject.Inject

class HomeUseCase @Inject constructor(
    val getGenreMovie: GetGenreMovie,
    val getPopularMovie: GetPopularMovie,
    val getNowPlayingMovie: GetNowPlayingMovie,
    val getMovieByGenre: GetMovieByGenre
)