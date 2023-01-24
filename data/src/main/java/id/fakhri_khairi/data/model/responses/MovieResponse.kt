package id.fakhri_khairi.data.model.responses

import com.squareup.moshi.Json

data class MovieResponse(
    @Json(name = "results")
    val result: List<MovieResultResponse>? = null
) {
    data class MovieResultResponse(
        @Json(name = "backdrop_path")
        val backdropPath: String? = null,

        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "original_title")
        val title: String? = null,

        @Json(name = "overview")
        val overview: String? = null,

        @Json(name = "poster_path")
        val posterPath: String? = null,
    )
}

data class MovieVideoResponse(
    @Json(name = "results")
    val result : List<VideoResultResponse>? = null
) {
    data class VideoResultResponse(
        @Json(name = "key")
        val key: String? = null,

        @Json(name = "official")
        val official: Boolean? = null
    )
}
