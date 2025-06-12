package com.project.onycheck.di

import com.project.onycheck.data.remote.NailApi
import com.project.onycheck.data.repository.NailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideNailApi(): NailApi {
        return Retrofit.Builder()
            .baseUrl("https://andeluw-naildetection.hf.space")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNailRepository(api: NailApi): NailRepository {
        return NailRepository(api)
    }
}