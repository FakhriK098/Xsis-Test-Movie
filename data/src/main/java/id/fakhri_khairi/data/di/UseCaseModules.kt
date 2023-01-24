package id.fakhri_khairi.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.fakhri_khairi.data.repo.contract.MovieRepository
import id.fakhri_khairi.data.usecase.contract.*
import id.fakhri_khairi.data.usecase.implement.*
import id.fakhri_khairi.data.usecase.implement.GetGenreMovieImpl
import id.fakhri_khairi.data.usecase.implement.GetMovieByGenreImpl
import id.fakhri_khairi.data.usecase.implement.GetMovieVideoImpl
import id.fakhri_khairi.data.usecase.implement.GetNowPlayingMovieImpl
import id.fakhri_khairi.data.usecase.implement.GetPopularMovieImpl

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModules {

    @Provides
    fun provideGetGenreMovie(repository: MovieRepository) : GetGenreMovie {
        return GetGenreMovieImpl(repository)
    }

    @Provides
    fun provideGetNowPlayingMovie(repository: MovieRepository) : GetNowPlayingMovie {
        return GetNowPlayingMovieImpl(repository)
    }

    @Provides
    fun provideGetPopularMovie(repository: MovieRepository) : GetPopularMovie {
        return GetPopularMovieImpl(repository)
    }

    @Provides
    fun provideGetMovieByGenre(repository: MovieRepository) : GetMovieByGenre {
        return GetMovieByGenreImpl(repository)
    }

    @Provides
    fun provideGetMovieVideo(repository: MovieRepository) : GetMovieVideo {
        return GetMovieVideoImpl(repository)
    }

    @Provides
    fun provideGetMovieRecommendation(repository: MovieRepository) : GetMovieRecommendation {
        return GetMovieRecommendationImpl(repository)
    }

    @Provides
    fun provideGetSearchMovie(repository: MovieRepository) : GetSearchMovie {
        return GetSearchMovieImpl(repository)
    }
}