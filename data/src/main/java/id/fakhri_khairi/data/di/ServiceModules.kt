package id.fakhri_khairi.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.fakhri_khairi.data.service.MovieService
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModules {

    @Singleton
    @Provides
    fun provideMovieService(
        @Named(DataModules.NAMED_OTP_RETROFIT) retrofit: Retrofit
    ): MovieService {
        return retrofit.create(MovieService::class.java)
    }

}