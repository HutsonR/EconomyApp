package com.backcube.data.remote.di

import dagger.Module

@Module(
    includes = [
        ApiModule::class,
        RemoteDataSourceModule::class,
        RetryModule::class,
    ]
)
interface DataRemoteModule