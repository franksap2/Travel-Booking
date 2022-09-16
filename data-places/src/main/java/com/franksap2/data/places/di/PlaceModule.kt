package com.franksap2.data.places.di

import com.franksap2.data.places.repository.PlaceRepository
import com.franksap2.data.places.repository.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaceModule {

    @Binds
    @Singleton
    internal abstract fun providePlaceRepository(placeRepositoryImpl: PlaceRepositoryImpl): PlaceRepository

}