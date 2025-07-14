package com.backcube.domain.di

import dagger.Module

@Module(
    includes = [
        QualifiersModule::class,
        UseCasesModule::class
    ]
)
interface DomainModule