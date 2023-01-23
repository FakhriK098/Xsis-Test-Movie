package id.fakhri_khairi.core.misc

fun String?.convertToImageTmdbUrl(width: Int) : String {
    return "https://image.tmdb.org/t/p/w$width$this"
}