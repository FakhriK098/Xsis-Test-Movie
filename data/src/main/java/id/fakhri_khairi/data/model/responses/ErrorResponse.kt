package id.fakhri_khairi.data.model.responses

import com.squareup.moshi.Json

data class ErrorResponse(
    @Json(name="status_code")
    val code: Int = 1,

    @Json(name="status_message")
    val message: String = "Terjadi kesalahan",
)