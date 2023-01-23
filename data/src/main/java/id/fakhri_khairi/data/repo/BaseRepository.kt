package id.fakhri_khairi.data.repo

abstract class BaseRepository {
    protected suspend fun <T> getData(onGetData: suspend () -> T): T {
        try {
            return onGetData()
        } catch (ex: Exception) {
            throw ex
        }
    }
}