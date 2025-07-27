package com.backcube.data.common.repositories.di

import com.backcube.data.local.di.DataLocalModule
import com.backcube.data.remote.di.DataRemoteModule
import dagger.Module

@Module(
    includes = [
        NetworkConnectivityModule::class,
        RepositoryModule::class,
        DataLocalModule::class,
        DataRemoteModule::class
    ]
)
interface DataModule