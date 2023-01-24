package id.fakhri_khairi.core.presentation.bottom_sheet

import id.fakhri_khairi.data.usecase.contract.GetMovieRecommendation
import id.fakhri_khairi.data.usecase.contract.GetMovieVideo
import javax.inject.Inject

class MovieDetailUseCase @Inject constructor(
    val getMovieVideo: GetMovieVideo,
    val getMovieRecommendation: GetMovieRecommendation
)