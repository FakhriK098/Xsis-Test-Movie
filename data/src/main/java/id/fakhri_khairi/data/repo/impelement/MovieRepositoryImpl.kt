package id.fakhri_khairi.data.repo.impelement

import id.fakhri_khairi.data.mapper.MovieMapper
import id.fakhri_khairi.data.misc.Constants
import id.fakhri_khairi.data.repo.BaseRepository
import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.service.MovieService
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Video
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val mapper: MovieMapper,
    private val service: MovieService
) : BaseRepository(), MovieRepository {
    override suspend fun getPopularMovie(page: Int): List<Movie> {
        return getData {
            val result = service.getPopularMovie(Constants.LANGUAGE, page).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getNowPlayingMovie(page: Int): List<Movie> {
        return getData {
            val result = service.getNowPlayingMovie(Constants.LANGUAGE, page).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getMovieByGenre(genreId: Int, page: Int): List<Movie> {
        return getData {
            val result = service.getMovieByGenre(genreId, page).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getMovieVideo(movieId: Int): List<Video> {
        return getData {
            val result = service.getMovieVideo(movieId, Constants.LANGUAGE).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getMovieRecommendation(movieId: Int): List<Movie> {
        return getData {
            val result = service.getMovieRecommendations(movieId, Constants.LANGUAGE, 1).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getSearchMovie(query: String): List<Movie> {
        return getData {
            val result = service.getSearchMovie(Constants.LANGUAGE, 1, query).result

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }

    override suspend fun getGenreMovie(): List<Genre> {
        return getData {
            val result = service.getGenreMovie(Constants.LANGUAGE).genres

            result?.map { mapper.convert(it) } ?: listOf()
        }
    }
}