package id.fakhri_khairi.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.newFixedThreadPoolContext
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@ObsoleteCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object CoreModules {

    @Singleton
    @Provides
    fun provideCoroutinesContext(): CoroutineContext {
        val numberOfCores = Runtime.getRuntime().availableProcessors()
        return newFixedThreadPoolContext(numberOfCores, THREAD_NAME) + SupervisorJob()
    }

    private const val THREAD_NAME = "adaptive_thread"
}