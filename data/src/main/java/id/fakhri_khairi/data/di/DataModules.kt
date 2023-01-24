package id.fakhri_khairi.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.fakhri_khairi.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModules {
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS).setKeyScheme(
            MasterKey.KeyScheme.AES256_GCM).build()
        return EncryptedSharedPreferences.create(
            context,
            ENCRYPTED_SHARED_PREFERENCES_FILE_NAME,
            masterKeyAlias,
            // prefKeyEncryptionScheme
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            // prefValueEncryptionScheme
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Named(NAMED_OTP_OKHTTP)
    fun provideOTPOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .addInterceptor(Interceptor {
                val accessTokenV4 = BuildConfig.ACCESS_TOKEN_V4

                val req = it.request()
                val newReq = req
                    .newBuilder()
                    .addHeader("authorization", "Bearer $accessTokenV4")
                    .build()

                return@Interceptor it.proceed(newReq)
            })
            .build()
    }

    @Provides
    @Named(NAMED_OTP_RETROFIT)
    fun provideOTPRetrofit(
        moshi: Moshi,
        @Named(NAMED_OTP_OKHTTP) okHttpClient: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    private const val ENCRYPTED_SHARED_PREFERENCES_FILE_NAME = "encrypted_shared_preferences"
    private const val NAMED_OTP_OKHTTP = "OTP_OKHTTP"
    const val NAMED_OTP_RETROFIT = "NAMED_OTP_RETROFIT"
    private const val TIMEOUT = 90L

}