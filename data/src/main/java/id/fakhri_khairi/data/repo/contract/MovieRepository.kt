package id.fakhri_khairi.data.repo.contract

import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Video

interface MovieRepository {
    suspend fun getPopularMovie(page: Int) : List<Movie>
    suspend fun getNowPlayingMovie(page: Int) : List<Movie>
    suspend fun getMovieByGenre(genreId: Int, page: Int) : List<Movie>
    suspend fun getMovieVideo(movieId: Int) : List<Video>
    suspend fun getMovieRecommendation(movieId: Int) : List<Movie>
    suspend fun getSearchMovie(query: String) : List<Movie>
    suspend fun getGenreMovie() : List<Genre>
}