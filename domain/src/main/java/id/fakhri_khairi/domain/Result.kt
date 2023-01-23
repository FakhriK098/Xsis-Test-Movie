package id.fakhri_khairi.domain

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(
        val errorType: ErrorType = ErrorType.UNKNOWN,
        val code: Int = ErrorType.UNKNOWN.code,
        val message: String
    ) : Result<Nothing>()
}

enum class ErrorType(val code: Int) {
    CLIENT(code = 400),
    SERVER(code = 500),
    NETWORK(code = 800),
    UNKNOWN(code = 900)
}
