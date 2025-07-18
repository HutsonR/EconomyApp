package com.backcube.data.di

import com.backcube.data.remote.di.ApiModule
import com.backcube.data.remote.di.DataSourceModule
import com.backcube.data.remote.di.NetworkModule
import com.backcube.data.remote.di.RepositoryModule
import com.backcube.data.remote.di.RetryModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        ApiModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        RetryModule::class
    ]
)
interface DataModule