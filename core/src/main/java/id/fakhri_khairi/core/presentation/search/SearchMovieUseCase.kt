package id.fakhri_khairi.core.presentation.search

import id.fakhri_khairi.data.usecase.contract.GetSearchMovie
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    val getSearchMovie: GetSearchMovie
)