package com.tomdroid.platformscience.interview.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tomdroid.platformscience.interview.data.local.MoshiJsonLoader
import com.tomdroid.platformscience.interview.data.local.MoshiJsonLoaderImpl
import com.tomdroid.platformscience.interview.data.repos.DriverRepo
import com.tomdroid.platformscience.interview.data.repos.DriverRepoImpl
import com.tomdroid.platformscience.interview.data.repos.ShipmentsRepo
import com.tomdroid.platformscience.interview.data.repos.ShipmentsRepoImpl
import com.tomdroid.platformscience.interview.models.RouteCalculator
import com.tomdroid.platformscience.interview.models.RouteCalculatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideDriverRepo(moshiJsonLoader: MoshiJsonLoader): DriverRepo {
        return DriverRepoImpl(
            moshiJsonLoader = moshiJsonLoader
        )
    }

    @Provides
    fun provideShipmentsRepo(moshiJsonLoader: MoshiJsonLoader): ShipmentsRepo{
        return ShipmentsRepoImpl(
            moshiJsonLoader = moshiJsonLoader
        )
    }

    @Provides
    fun provideRouteCalculator(): RouteCalculator {
        return RouteCalculatorImpl()
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideJsonLoader(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): MoshiJsonLoader {
        return MoshiJsonLoaderImpl(
            context = context,
            moshi = moshi
        )
    }


}