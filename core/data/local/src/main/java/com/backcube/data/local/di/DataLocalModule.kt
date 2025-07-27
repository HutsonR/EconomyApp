package com.backcube.data.local.di

import dagger.Module

@Module(
    includes = [
        LocalDataSourceModule::class,
        StorageModule::class
    ]
)
interface DataLocalModule