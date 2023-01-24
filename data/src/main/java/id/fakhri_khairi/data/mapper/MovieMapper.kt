package id.fakhri_khairi.data.mapper

import id.fakhri_khairi.data.model.responses.GenreResponse
import id.fakhri_khairi.data.model.responses.MovieResponse
import id.fakhri_khairi.data.model.responses.MovieVideoResponse
import id.fakhri_khairi.domain.Genre
import id.fakhri_khairi.domain.Movie
import id.fakhri_khairi.domain.Video

class MovieMapper {
    fun convert(from: MovieResponse.MovieResultResponse) : Movie {
        return Movie(
            id = from.id ?: 0,
            title = from.title ?: "",
            overview = from.overview ?: "",
            posterPath = from.posterPath ?: "",
            backdropPath = from.backdropPath ?: ""
        )
    }

    fun convert(from: GenreResponse.GenreItemResponse): Genre {
        return Genre(
            id = from.id ?: 0,
            name = from.name ?: ""
        )
    }

    fun convert(from: MovieVideoResponse.VideoResultResponse) : Video {
        return Video(
            key = from.key ?: "",
            official = from.official ?: false
        )
    }
}