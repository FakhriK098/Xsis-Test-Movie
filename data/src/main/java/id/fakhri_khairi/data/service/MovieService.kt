package id.fakhri_khairi.data.service

import id.fakhri_khairi.data.model.responses.GenreResponse
import id.fakhri_khairi.data.model.responses.MovieResponse
import id.fakhri_khairi.data.model.responses.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(value = "3/movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MovieResponse

    @GET(value = "3/movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MovieResponse

    @GET(value = "3/genre/movie/list")
    suspend fun getGenreMovie(
        @Query("language") language: String
    ) : GenreResponse

    @GET(value = "4/list/{genre_id}")
    suspend fun getMovieByGenre(
        @Path("genre_id") genreId: Int,
        @Query("page") page: Int
    ) : MovieResponse

    @GET(value = "3/movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String
    ) : MovieVideoResponse

    @GET(value = "3/movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : MovieResponse

    @GET(value = "3/search/movie")
    suspend fun getSearchMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ) : MovieResponse
}