package com.toolslab.tractorlocator.base_network.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.toolslab.tractorlocator.base_network.ApiEndpoint.API_BASE_URL
import com.toolslab.tractorlocator.base_network.Reauthenticator
import com.toolslab.tractorlocator.base_network.service.ApiAuthService
import com.toolslab.tractorlocator.base_network.service.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideApiService(@Named(API) retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

    @Provides
    @Named(API)
    fun provideRetrofit(@Named(API) okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()

    @Provides
    @Named(API)
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor, reauthenticator: Reauthenticator) =
            OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .authenticator(reauthenticator)
                    .build()

    @Provides
    fun provideApiAuthService(@Named(AUTH_API) retrofit: Retrofit): ApiAuthService =
            retrofit.create(ApiAuthService::class.java)

    @Provides
    @Named(AUTH_API)
    fun provideAuthRetrofit(@Named(AUTH_API) okHttpClient: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .client(okHttpClient)
                    .build()

    @Provides
    @Named(AUTH_API)
    fun provideOkHttpAuthClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
            OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = BASIC
        return httpLoggingInterceptor
    }

    @Provides
    fun provideMoshi(): Moshi =
            Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

    companion object {
        // Avoid cyclic dependency for Reauthenticator used in OkHttpClient
        // See difference between provideOkHttpClient() and provideOkHttpAuthClient()
        private const val API = "API"
        private const val AUTH_API = "AUTH_API"
    }

}
