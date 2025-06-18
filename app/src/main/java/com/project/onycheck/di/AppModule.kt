package com.project.onycheck.di

import com.project.onycheck.BuildConfig
import com.project.onycheck.data.remote.NailApi
import com.project.onycheck.data.remote.PlacesApi
import com.project.onycheck.data.repository.NailRepository
import com.project.onycheck.data.repository.PlacesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    // --- Nail Detection API Setup ---

    @Provides
    @Singleton
    @Named("NailApi") // ✅ Name this provider "NailApi"
    fun provideNailRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL) // Uses your nail detection URL
//            .baseUrl("https://andeluw-naildetection.hf.space/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNailApi(@Named("NailApi") retrofit: Retrofit): NailApi { // ✅ Inject the named Retrofit
        return retrofit.create(NailApi::class.java)
    }

    // This provider is correct and doesn't need to change
    @Provides
    @Singleton
    fun provideNailRepository(api: NailApi): NailRepository {
        return NailRepository(api)
    }

    // --- Google Places API Setup ---

    @Provides
    @Singleton
    @Named("PlacesApi") // ✅ Name this provider "PlacesApi"
    fun providePlacesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/") // Uses the Google Maps base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePlacesApi(@Named("PlacesApi") retrofit: Retrofit): PlacesApi { // ✅ Inject the named Retrofit
        return retrofit.create(PlacesApi::class.java)
    }

    // Note: You don't need a provider for PlacesRepository if its constructor
    // is annotated with @Inject, as Hilt can create it automatically now
    // that it knows how to provide PlacesApi.
}