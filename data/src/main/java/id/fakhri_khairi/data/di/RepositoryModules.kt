package id.fakhri_khairi.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.fakhri_khairi.data.mapper.MovieMapper
import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.repo.impelement.MovieRepositoryImpl
import id.fakhri_khairi.data.service.MovieService

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModules {

    @Provides
    fun provideMovieRepository(
        movieMapper: MovieMapper,
        movieService: MovieService
    ) : MovieRepository {
        return MovieRepositoryImpl(
            mapper = movieMapper,
            service = movieService
        )
    }

}