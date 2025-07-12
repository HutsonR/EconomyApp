package com.backcube.economyapp.domain.di

import dagger.Module

@Module(
    includes = [
        QualifiersModule::class,
        UseCasesModule::class
    ]
)
interface DomainModule