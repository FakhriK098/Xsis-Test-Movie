package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetPopularMovie
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import javax.inject.Inject

internal class GetPopularMovieImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Movie>>(), GetPopularMovie {
    override suspend fun execute(page: Int): Result<List<Movie>> = getSuspendResult {
        repository.getPopularMovie(page = page)
    }
}