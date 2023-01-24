package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetSearchMovie
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import javax.inject.Inject

internal class GetSearchMovieImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Movie>>(), GetSearchMovie {
    override suspend fun execute(query: String): Result<List<Movie>> = getSuspendResult {
        repository.getSearchMovie(query)
    }
}