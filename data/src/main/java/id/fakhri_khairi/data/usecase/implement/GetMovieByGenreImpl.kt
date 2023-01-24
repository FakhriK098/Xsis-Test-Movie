package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetMovieByGenre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import javax.inject.Inject

internal class GetMovieByGenreImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Movie>>(), GetMovieByGenre {
    override suspend fun execute(genreId: Int, page: Int): Result<List<Movie>> = getSuspendResult {
        repository.getMovieByGenre(genreId, page)
    }
}