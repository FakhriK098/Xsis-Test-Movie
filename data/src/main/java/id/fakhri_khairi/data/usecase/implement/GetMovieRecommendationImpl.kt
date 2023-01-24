package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetMovieRecommendation
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import javax.inject.Inject

internal class GetMovieRecommendationImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Movie>>(), GetMovieRecommendation {
    override suspend fun execute(movieId: Int): Result<List<Movie>> = getSuspendResult {
        repository.getMovieRecommendation(movieId)
    }
}