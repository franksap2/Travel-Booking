package com.franksap2.travelbooking

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TravelBookingApplication : Application() , ImageLoaderFactory{

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .apply {
                diskCache {
                    DiskCache.Builder()
                        .directory(applicationContext.cacheDir.resolve("image_cache"))
                        .build()
                }
                logger(DebugLogger())
            }.build()
    }
}