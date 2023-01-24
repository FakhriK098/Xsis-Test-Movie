package id.fakhri_khairi.data.usecase.contract

import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Result
import id.fakhri_khairi.domain.Video

interface GetGenreMovie {
    suspend fun execute() : Result<List<Genre>>
}

interface GetPopularMovie {
    suspend fun execute(page: Int) : Result<List<Movie>>
}

interface GetNowPlayingMovie {
    suspend fun execute(page: Int) : Result<List<Movie>>
}

interface GetMovieByGenre {
    suspend fun execute(genreId: Int, page: Int) : Result<List<Movie>>
}

interface GetMovieVideo {
    suspend fun execute(movieId: Int) : Result<List<Video>>
}

interface GetMovieRecommendation {
    suspend fun execute(movieId: Int) : Result<List<Movie>>
}

interface GetSearchMovie {
    suspend fun execute(query: String) : Result<List<Movie>>
}