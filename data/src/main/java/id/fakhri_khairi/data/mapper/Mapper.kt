package id.fakhri_khairi.data.mapper

interface Mapper<INPUT, OUTPUT> {
    fun convert(from: INPUT): OUTPUT
}