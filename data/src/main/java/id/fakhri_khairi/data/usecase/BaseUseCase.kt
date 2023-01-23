package id.fakhri_khairi.data.usecase

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import id.fakhri_khairi.domain.Result
import id.fakhri_khairi.domain.ErrorType

abstract class BaseUseCase<T> {
    protected suspend fun getSuspendResult(onSuccess: suspend () -> T): Result<T> {
        return try {
            val data = onSuccess()
            Result.Success(data = data)
        } catch (ex: Exception) {
            catchError(ex)
        }
    }

    protected fun getResult(onSuccess: () -> T): Result<T> {
        return try {
            val data = onSuccess()
            Result.Success(data = data)
        } catch (ex: Exception) {
            catchError(ex)
        }
    }

    private fun catchError(ex: Exception): Result.Error {
        ex.printStackTrace()

        var errorType = ErrorType.UNKNOWN
        var message = ex.message ?: "Oops something went wrong"
        var errorCode = 0

        when (ex) {
            is UnknownHostException, is SocketTimeoutException, is ConnectException -> {
                errorType = ErrorType.NETWORK
                message = "No Internet connection"
            }
            is HttpException -> {
                errorCode = ex.code()
                message = ex.message()
                errorType = when {
                    errorCode >= CODE_600 -> ErrorType.UNKNOWN
                    errorCode >= CODE_500 -> ErrorType.SERVER
                    errorCode >= CODE_400 -> ErrorType.CLIENT
                    else -> ErrorType.UNKNOWN
                }
            }
        }

        return Result.Error(errorType = errorType, code = errorCode, message = message)
    }

    companion object {
        private const val CODE_400 = 400
        private const val CODE_500 = 500
        private const val CODE_600 = 600
    }
}
