package id.fakhri_khairi.domain

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    val posterPath: String = "",
    val backdropPath: String = "",
    val viewType: Int = 0
)
