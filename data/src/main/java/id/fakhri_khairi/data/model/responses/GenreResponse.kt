package id.fakhri_khairi.data.model.responses

import com.squareup.moshi.Json

data class GenreResponse(
    @Json(name = "genres")
    val genres : List<GenreItemResponse>? = null
) {
    data class GenreItemResponse(
        @Json(name = "id")
        val id: Int? = null,

        @Json(name = "name")
        val name: String? = null
    )
}
