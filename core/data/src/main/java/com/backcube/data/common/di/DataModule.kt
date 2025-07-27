package com.backcube.data.common.di

import com.backcube.data.remote.di.ApiModule
import com.backcube.data.remote.di.NetworkModule
import com.backcube.data.remote.di.RemoteDataSourceModule
import com.backcube.data.remote.di.RepositoryModule
import com.backcube.data.remote.di.RetryModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        ApiModule::class,
        RepositoryModule::class,
        RemoteDataSourceModule::class,
        RetryModule::class,
    ]
)
interface DataModule