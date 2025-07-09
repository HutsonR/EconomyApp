package com.backcube.economyapp.data.di

import com.backcube.economyapp.data.remote.di.ApiModule
import com.backcube.economyapp.data.remote.di.NetworkModule
import com.backcube.economyapp.data.remote.di.RepositoryModule
import com.backcube.economyapp.data.remote.di.RetryModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        ApiModule::class,
        RepositoryModule::class,
        RetryModule::class
    ]
)
interface DataModule