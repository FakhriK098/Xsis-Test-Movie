package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetGenreMovie
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Result
import javax.inject.Inject

internal class GetGenreMovieImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Genre>>(), GetGenreMovie {
    override suspend fun execute(): Result<List<Genre>> = getSuspendResult {
        repository.getGenreMovie()
    }
}