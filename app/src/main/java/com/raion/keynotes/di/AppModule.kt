package com.raion.keynotes.di

import com.raion.keynotes.network.RaionAPI
import com.raion.keynotes.repository.RaionRepository
import com.raion.keynotes.util.Constant
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
    @Singleton
    @Provides
    fun provideRaionRepository(api: RaionAPI) = RaionRepository(api)

    @Singleton
    @Provides
    fun provideRaionAPI(): RaionAPI{
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RaionAPI::class.java)
    }
}