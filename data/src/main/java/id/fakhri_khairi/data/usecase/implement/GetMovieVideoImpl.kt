package id.fakhri_khairi.data.usecase.implement

import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.BaseUseCase
import id.fakhri_khairi.data.usecase.contract.GetMovieVideo
import id.fakhri_khairi.domain.Result
import id.fakhri_khairi.domain.Video
import javax.inject.Inject

internal class GetMovieVideoImpl @Inject constructor(
    private val repository: MovieRepository
) : BaseUseCase<List<Video>>(), GetMovieVideo {
    override suspend fun execute(movieId: Int): Result<List<Video>> = getSuspendResult {
        repository.getMovieVideo(movieId)
    }
}